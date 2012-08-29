package org.apache.maven.model.interpolation;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Builds an interpolation tree, describing every single possible interpolation for
 * a given pom instance. Does not actually *do* any interpolations
 */
public class InterpolationTreeBuilder
{
    public Elements buildTree( Object root )
        throws IllegalAccessException
    {
        List<Element> container = buildChildren( root );
        if ( container == null )  // Nothing to do.
        {
            container = new ArrayList<Element>(  );
        }
        return new Elements( container.toArray( new Element[container.size()] ) );
    }

    class Elements
    {
        private final Element[] elements;

        Elements( Element[] element )
        {
            this.elements = element;
        }

        public void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException
        {
            for ( Element element : elements )
            {
                element.interpolate( target, interpolator );
            }
        }

        public String toString()
        {
            StringBuilder result = new StringBuilder();
            for ( Element element : elements )
            {
                result.append( element.toString() );
                result.append( "\n" );
            }
            return result.toString();
        }

    }

    private List<Element> buildChildren( Object object )
        throws IllegalAccessException
    {
        if ( object == null )
        {
            return null;
        }
        List<Element> children = new ArrayList<Element>();

        InterpolationCache.CacheItem cacheEntry = InterpolationCache.getCacheEntry( object.getClass() );
        if ( cacheEntry.isArray )
        {
            List<Element> kiz = new ArrayList<Element>();
            int len = Array.getLength( object );
            for ( int j = 0; j < len; j++ )
            {

                Object item = Array.get( object, j );
                children.add( new DirectArrayContainer( kiz, j ) );

                if ( item instanceof String )
                {
                    if ( isInterpolationPossible( (String) item ) )
                    {
                        Element indexedItem = new DirectArrayEntryInterpolatable( j, (String) item );
                        kiz.add( indexedItem );
                    }
                }
                else
                {
                    List<Element> build = buildChildren( item );
                    if ( build != null )
                    {
                        kiz.add( new DirectArrayContainer( build, j ) );
                    }
                }
            }
            return kiz.size() > 0 ? kiz : null;
        }
        else
        {
            for ( InterpolationCache.CacheField currentField : cacheEntry.fields )
            {
                currentField.field.setAccessible( true );
                Object value = currentField.field.get( object );

                if ( currentField.isString )
                {
                    buildStringEntry( children, currentField.field, (String) value );
                }
                else if ( currentField.isList )
                {
                    buildFullListItem( children, value, currentField.field );
                }
                else if ( currentField.isMap )
                {
                    if ( "locations".equals( currentField.field.getName() ) )
                    {
                        continue;
                    }
                    Map aMap = (Map) value;
                    if ( aMap != null )
                    {
                        for ( Object entry : aMap.entrySet() )
                        {
                            buildMapItem( children, currentField.field, (Map.Entry) entry );
                        }
                    }
                }
                else
                {
                    buildObjectEntry( children, currentField.field, value );
                }
            }
        }
        return children.size() > 0 ? children : null;
    }

    private void buildFullListItem( List<Element> children, Object value, Field currentField )
        throws IllegalAccessException
    {
        @SuppressWarnings( "unchecked" ) List<Object> c = (List<Object>) value;
        if ( c != null )
        {
            int size = c.size();
            for ( int i = 0; i < size; i++ )
            {
                Object itemValue = c.get( i );
                if ( itemValue != null )
                {
                    Class<?> classOfValye = itemValue.getClass();
                    if ( String.class == classOfValye )
                    {
                        if ( isInterpolationPossible( itemValue ) )
                        {
                            children.add( new ListItemInterpolatable( currentField, i, (String) itemValue ) );
                        }
                    }
                    else
                    {

                        List<Element> elements = buildChildren( itemValue );
                        if ( elements != null )
                        {
                            children.add( new ListContainer( currentField, elements, i ) );
                        }
                    }
                }
            }
        }
    }

    private void buildStringEntry( List<Element> children, Field currentField, String value )
    {
            if ( isInterpolationPossible( value ) )
            {
                children.add( new StringMemberInterpolatable( currentField, value ) );
            }
    }

    private void buildObjectEntry( List<Element> children, Field currentField, Object value )
        throws IllegalAccessException
    {
        List<Element> build = buildChildren( value );
        if ( build != null )
        {
            children.add( new ObjectContainer( currentField, build ) );
        }
    }

    private void buildMapItem( List<Element> children, Field currentField, Map.Entry item )
        throws IllegalAccessException
    {
        Object value1 = item.getValue();
        if ( value1 instanceof String )
        {
            if ( isInterpolationPossible( value1 ) )
            {
                children.add( new MapEntryInterpolatable( currentField, item.getKey(), (String) value1 ) );
            }
        }
        else
        {
            List<Element> build = buildChildren( value1 );
            if ( build != null )
            {
                children.add( new MapItem( currentField, build, item.getKey() ) );
            }
        }
    }

    private boolean isInterpolationPossible( String value )
    {
        return value != null && value.contains( "${" );
    }

    private boolean isInterpolationPossible( Object value )
    {
        return value instanceof String && ( (String) value ).contains( "${" );
    }


    static class MapItem
        extends MyContainerImpl
    {
        private final Object key;

        MapItem( Field currentField, List<Element> target, Object key )
        {
            super( currentField, target );
            this.key = key;
        }

        @Override
        protected Object getTarget( Object target )
            throws IllegalAccessException
        {
            Map map = (Map) field.get( target );
            return map.get( key );
        }

    }


    interface Element
    {

        void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException;
    }

    interface Interpolator
    {
        public String interpolate( String original );
    }

    /**
     * An interpolatable represents any value that can be interpolated and is a leaf node
     */
    interface MyInterpolable
        extends Element
    {
    }

    static class MapEntryInterpolatable
        extends MyField
        implements MyInterpolable
    {
        private final Object key;

        private final String originalValue;

        MapEntryInterpolatable( Field currentField, Object key, String originalValue )
        {
            super( currentField );
            this.key = key;
            this.originalValue = originalValue;
        }

        public void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException
        {

            String s = interpolator.interpolate( originalValue );
            @SuppressWarnings( "unchecked" ) Map<Object, String> map = (Map) field.get( target );
            try
            {
                map.put( key, s );
            }
            catch ( UnsupportedOperationException ignore )
            {

            }

        }

        @Override
        public String toString()
        {
            return field.getName() + "[" + key + "]";
        }
    }

    static class DirectArrayEntryInterpolatable
        implements MyInterpolable
    {
        private final int pos;

        private final String originalValue;

        public void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException
        {
            String s = interpolator.interpolate( originalValue );
            Array.set( target, pos, s );
        }

        DirectArrayEntryInterpolatable( int pos, String originalValue )
        {
            this.pos = pos;
            this.originalValue = originalValue;
        }


        @Override
        public String toString()
        {
            return "[" + pos + "]";
        }
    }


    static class ListItemInterpolatable
        extends MyField
        implements MyInterpolable
    {
        private final int pos;

        private final String originalValue;

        ListItemInterpolatable( Field currentField, int pos, String originalValue )
        {
            super( currentField );
            this.pos = pos;
            this.originalValue = originalValue;
        }

        public void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException
        {

            String s = interpolator.interpolate( originalValue );
            @SuppressWarnings( "unchecked" ) List<Object> list = (List) field.get( target );
            try
            {
                list.set( pos, s );
            }
            catch ( UnsupportedOperationException ignore )
            {

            }
        }


        @Override
        public String toString()
        {
            return field.getName() + "[" + pos + "]";
        }
    }

    static class StringMemberInterpolatable
        extends MyField
        implements MyInterpolable
    {
        private final String originalValue;

        StringMemberInterpolatable( Field currentField, String originalValue )
        {
            super( currentField );
            this.originalValue = originalValue;
        }

        public void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException
        {
            String s = interpolator.interpolate( originalValue );
            field.set( target, s );
        }
    }

    static Element[] toArray(List<Element> items){
        return items.toArray( new Element[items.size()]);
    }

    static class DirectArrayContainer
        implements Element
    {
        private final int pos;

        private final Element[] children;

        DirectArrayContainer( List<Element> children, int pos )
        {
            this.children = toArray( children );
            this.pos = pos;
        }

        public void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException
        {
            Object contained = getTarget( target );
            for ( Element child : children )
            {
                child.interpolate( contained, interpolator );
            }
        }


        protected Object getTarget( Object target )
            throws IllegalAccessException
        {
            return Array.get( target, pos );
        }

        @Override
        public String toString()
        {
            StringBuilder result = new StringBuilder();

            for ( Element child : children )
            {
                result.append( "[" ).append( pos ).append( "]." ).append( child.toString() );
            }
            return result.toString();
        }
    }

    static class ListContainer
        extends MyContainerImpl
    {
        private final int pos;

        ListContainer( Field currentField, List<Element> children, int pos )
        {
            super( currentField, children );
            this.pos = pos;
        }


        @Override
        protected Object getTarget( Object target )
            throws IllegalAccessException
        {
            List list = (List) field.get( target );
            return list.get( pos );
        }

        @Override
        public String toString()
        {
            StringBuilder result = new StringBuilder();

            for ( Element child : children )
            {
                result.append( field.getName() ).append( "[" ).append( pos ).append( "]." ).append( child.toString() );
            }
            return result.toString();
        }
    }

    static class ObjectContainer
        extends MyContainerImpl
    {
        ObjectContainer( Field currentField, List<Element> build )
        {
            super( currentField, build );
        }


        public List<String> getStrings()
        {
            return getPrefixedStrings( field.getName() + ".", Arrays.asList( children ));
        }

        @Override
        protected Object getTarget( Object target )
            throws IllegalAccessException
        {
            return field.get( target );
        }

        @Override
        public String toString()
        {
            StringBuilder result = new StringBuilder();
            for ( String s : getStrings() )
            {
                result.append( s );
            }
            return result.toString();
        }
    }

    static abstract class MyField
        implements Element
    {
        protected final Field field;

        MyField( Field field )
        {
            this.field = field;
        }

        protected Object resolve( Object target )
            throws IllegalAccessException
        {
            return field.get( target );
        }


        @Override
        public String toString()
        {
            return field.getName();
        }

        public String thisToString(){
           return "";
        }
    }

    static abstract class MyContainerImpl
        extends MyField
    {
        protected final Element[] children;

        MyContainerImpl( Field field, List<Element> children )
        {
            super( field );
            this.children = toArray( children );
        }

        protected abstract Object getTarget( Object target )
            throws IllegalAccessException;

        public void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException
        {
            Object contained = getTarget( target );
            for ( Element child : children )
            {
                child.interpolate( contained, interpolator );
            }
        }
    }


    private static List<String> getPrefixedStrings( String prefix, List<Element> kids )
    {
        List<String> result = new ArrayList<String>();
        for ( Element child : kids )
        {
            result.add( prefix + child.toString() );
        }
        return result;
    }
}
