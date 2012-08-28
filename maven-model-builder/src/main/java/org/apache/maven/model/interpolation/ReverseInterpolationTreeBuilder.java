package org.apache.maven.model.interpolation;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.crypto.NullCipher;

/**
 * Builds an interpolation tree, describing every single possible interpolation for
 * a given pom instance. Does not actually *do* any interpolations
 */
public class ReverseInterpolationTreeBuilder
{
    List<NuElement> leaves = new ArrayList<NuElement>(  );
    public NuElement buildTree( Object root )
        throws IllegalAccessException
    {
        NuElement rootElem = new NuElement();
        buildChildren( rootElem, root );
        return rootElem;
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

    private void buildChildren( NuElement parentElement, Object object )
        throws IllegalAccessException
    {
        if ( object == null )
        {
            return;
        }

        InterpolationCache.CacheItem cacheEntry = InterpolationCache.getCacheEntry( object.getClass() );
        if ( cacheEntry.isArray )
        {
            int len = Array.getLength( object );
            for ( int j = 0; j < len; j++ )
            {

                NuElement kiz = new DirectArrayContainer( parentElement, j ) ;
                Object item = Array.get( object, j );

                if ( item instanceof String )
                {
                    if ( isInterpolationPossible( (String) item ) )
                    {
                        NuElement indexedItem = new DirectArrayEntryInterpolatable(  j, (String) item, kiz );
                        leaves.add(  indexedItem );
                    }
                }
                else
                {
                    buildChildren( kiz, item );
                }
            }
        }
        else
        {
            for ( InterpolationCache.CacheField currentField : cacheEntry.fields )
            {
                currentField.field.setAccessible( true );
                Object value = currentField.field.get( object );

                if ( currentField.isString )
                {
                    buildStringEntry( parentElement, currentField.field, (String) value );
                }
                else if ( currentField.isList )
                {
                    buildFullListItem( parentElement, value, currentField.field );
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
                            buildMapItem( parentElement, currentField.field, (Map.Entry) entry );
                        }
                    }
                }
                else
                {
                    buildObjectEntry( parentElement, currentField.field, value );
                }
            }
        }
    }

    private void buildFullListItem( NuElement parent, Object value, Field currentField )
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
                            leaves.add( new ListItemInterpolatable( parent, currentField, i, (String) itemValue ) );
                        }
                    }
                    else
                    {

                        buildChildren( new ListContainer( currentField, parent, i ), itemValue );
                    }
                }
            }
        }
    }

    private void buildStringEntry( NuElement parentElement, Field currentField, String value )
    {
            if ( isInterpolationPossible( value ) )
            {
                leaves.add( new StringMemberInterpolatable( parentElement, currentField, value ) );
            }
    }

    private void buildObjectEntry( NuElement parent, Field currentField, Object value )
        throws IllegalAccessException
    {
        buildChildren( new ObjectContainer( currentField, parent), value );
    }


    private void buildMapItem( NuElement parent, Field currentField, Map.Entry item )
        throws IllegalAccessException
    {
        Object value1 = item.getValue();
        if ( value1 instanceof String )
        {
            if ( isInterpolationPossible( value1 ) )
            {
                leaves.add( new MapEntryInterpolatable( parent, currentField, item.getKey(), (String) value1 ) );
            }
        }
        else
        {
            buildChildren( new MapItem(currentField, parent, item.getKey()),  value1 );
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
        extends NuElement
    {
        private final Field currentField;

        private final NuElement parent;

        private final Object key;

        MapItem( Field currentField, NuElement parent , Object key )
        {
            this.currentField = currentField;
            this.parent = parent;
            this.key = key;
        }

        public Object getValue( Object target )
            throws IllegalAccessException
        {
            return currentField.get( parent.getValue( target ));
        }

    }


    interface Element
    {

        void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException;
    }

    static class NuElement {

        public Object getValue(Object root)
            throws IllegalAccessException
        {
            return root;
        }
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
        extends NuElement
    {
        private final NuElement parent;

        private final Field currentField;

        private final Object key;

        private final String originalValue;

        MapEntryInterpolatable( NuElement parent, Field currentField, Object key, String originalValue )
        {
            this.parent = parent;
            this.currentField = currentField;
            this.key = key;
            this.originalValue = originalValue;
        }

        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            @SuppressWarnings( "unchecked" ) Map<Object, String> map = (Map) currentField.get( parent.getValue( root ));
//            String s = interpolator.interpolate( originalValue );
            return map.get(  key );
        }


        @Override
        public String toString()
        {
            return parent.toString()+ currentField.getName() + "[" + key + "]";
        }
    }

    static class ArrayEntryInterpolatable
        extends MyField
        implements MyInterpolable
    {
        private final int pos;

        private final String originalValue;

        ArrayEntryInterpolatable( Field currentField, int pos, String originalValue )
        {
            super( currentField );
            this.pos = pos;
            this.originalValue = originalValue;
        }

        public void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException
        {

            String s = interpolator.interpolate( originalValue );
            Object array = field.get( target );
            Array.set( array, pos, s );
        }


        @Override
        public String toString()
        {
            return field.getName() + "[" + pos + "]";
        }
    }

    static class DirectArrayEntryInterpolatable
        extends NuElement
    {
        private final int pos;

        private final String originalValue;

        private final NuElement parent;


        public void interpolate( Object target, Interpolator interpolator )
            throws IllegalAccessException
        {
            String s = interpolator.interpolate( originalValue );

            Array.set( target, pos, s );
        }

        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            return Array.get( parent.getValue( root ), pos );
        }

        DirectArrayEntryInterpolatable( int pos, String originalValue, NuElement parent )
        {
            this.pos = pos;
            this.originalValue = originalValue;
            this.parent = parent;
        }


        @Override
        public String toString()
        {
            return parent.toString() + "[" + pos + "]";
        }
    }


    static class ListItemInterpolatable
        extends NuElement
    {
        private final NuElement parent;

        private final Field currentField;

        private final int pos;

        private final String originalValue;

        ListItemInterpolatable( NuElement parent, Field currentField, int pos, String originalValue )
        {
            this.parent = parent;
            this.currentField = currentField;
            this.pos = pos;
            this.originalValue = originalValue;
        }

        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            return currentField.get( parent.getValue(  root ));
        }


        @Override
        public String toString()
        {
            return parent.toString() + "." + currentField.getName() + "[" + pos + "]";
        }
    }

    static class StringMemberInterpolatable
        extends NuElement
    {
        private final Field currentField;

        private final String originalValue;
        private final NuElement parent;

        StringMemberInterpolatable( NuElement parent, Field currentField, String originalValue )
        {
            this.parent = parent;
            this.currentField = currentField;
            this.originalValue = originalValue;
        }


        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            return currentField.get(  super.getValue(  root ) );
        }

    }

    static Element[] toArray(List<Element> items){
        return items.toArray( new Element[items.size()]);
    }

    static class DirectArrayContainer
        extends NuElement
    {
        private final int pos;

        private final NuElement parent;

        DirectArrayContainer( NuElement parent, int pos )
        {
            this.parent = parent;
            this.pos = pos;
        }


        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            return Array.get( super.getValue( root ), pos);
        }


        @Override
        public String toString()
        {
            return parent.toString()+ "[" +pos + "].";
        }
    }

    static class ArrayContainer
        extends MyContainerImpl
    {
        private final int pos;

        ArrayContainer( Field currentField, List<Element> children, int pos )
        {
            super( currentField, children );
            this.pos = pos;
        }

        @Override
        protected Object getTarget( Object target )
            throws IllegalAccessException
        {
            Object array = field.get( target );
            return Array.get( array, pos );
        }

        @Override
        public String toString()
        {
            StringBuilder result = new StringBuilder();

            for ( Element child : children )
            {
                result.append( field.getName() + "[" + pos + "]." + child.toString() );
            }
            return result.toString();
        }
    }

    static class ListContainer
        extends NuElement
    {
        private final Field currentField;

        private final NuElement parent;

        private final int pos;

        ListContainer( Field currentField, NuElement parent, int pos )
        {
            this.currentField = currentField;
            this.parent = parent;
            this.pos = pos;
        }


        @Override
        public Object getValue( Object target )
            throws IllegalAccessException
        {

            return currentField.get(  parent.getValue(  target ) );
        }

        @Override
        public String toString()
        {
            return parent.toString() + currentField.getName() + "[" + pos + "]";
        }
    }

    static class ObjectContainer
        extends NuElement
    {
        private final Field currentField;

        private final NuElement parent;

        ObjectContainer( Field currentField, NuElement parent)
        {
            this.currentField = currentField;
            this.parent = parent;
        }


        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            return currentField.get( parent.getValue(  root ));
        }

        @Override
        public String toString()
        {
            return parent.toString() + "." + currentField.getName();
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
