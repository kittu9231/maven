package org.apache.maven.model.interpolation;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Builds an interpolation tree, describing every single possible interpolation for
 * a given pom instance. Does not actually *do* any interpolations
 */
public class ReverseInterpolationTreeBuilder
{
    List<Leaf> leaves = new ArrayList<Leaf>();

    public Elements buildTree( Object root )
        throws IllegalAccessException
    {
        buildChildren( new NuElement(), root );
        return new Elements( leaves );
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

                Object item = Array.get( object, j );

                if ( item instanceof String )
                {
                    if ( isInterpolationPossible( (String) item ) )
                    {
                        DirectArrayEntryInterpolatable indexedItem =
                            new DirectArrayEntryInterpolatable( j, (String) item, parentElement );
                        leaves.add( indexedItem );
                    }
                }
                else
                {
                    NuElement kiz = new DirectArrayContainer( parentElement, j );
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
        @SuppressWarnings("unchecked") List<Object> c = (List<Object>) value;
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
        buildChildren( new ObjectContainer( currentField, parent ), value );
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
            buildChildren( new MapItem( currentField, parent, item.getKey() ), value1 );
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

        MapItem( Field currentField, NuElement parent, Object key )
        {
            this.currentField = currentField;
            this.parent = parent;
            this.key = key;
        }

        public Object getValue( Object target )
            throws IllegalAccessException
        {
            Map map = (Map) currentField.get( parent.getValue( target ) );
            return map.get( key );
        }

    }


    interface Leaf
    {
        public String getOriginalValue();

        void setValue( Object root, String value )
            throws IllegalAccessException;
    }


    static class NuElement
    {

        public Object getValue( Object root )
            throws IllegalAccessException
        {
            return root;
        }

        public String toString()
        {
            return null;
        }

    }

    static class FieldBoundNuElement
        extends NuElement
    {
        protected final NuElement parent;

        protected final Field currentField;

        FieldBoundNuElement( NuElement parent, Field currentField )
        {
            this.parent = parent;
            this.currentField = currentField;
        }

        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            return currentField.get( parent.getValue( root ) );
        }

        @Override
        public String toString()
        {
            String s = parent.toString();
            return s != null ? s + "." + currentField.getName() : currentField.getName();
        }
    }

    interface Interpolator
    {
        public String interpolate( String original );
    }

    static class MapEntryInterpolatable
        extends FieldBoundNuElement
        implements Leaf
    {
        private final Object key;

        private final String originalValue;

        MapEntryInterpolatable( NuElement parent, Field currentField, Object key, String originalValue )
        {
            super( parent, currentField );
            this.key = key;
            this.originalValue = originalValue;
        }


        public String getOriginalValue()
        {
            return originalValue;
        }

        public void setValue( Object root, String value )
            throws IllegalAccessException
        {
            @SuppressWarnings( "unchecked" ) Map<Object, String> map =
                (Map) currentField.get( parent.getValue( root ) );

            try
            {
                map.put( key, value );
            }
            catch ( java.lang.UnsupportedOperationException ignore )
            {
            }

        }

        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            @SuppressWarnings("unchecked") Map<Object, String> map = (Map) currentField.get( parent.getValue( root ) );
            return map.get( key );
        }


        @Override
        public String toString()
        {
            String s = parent.toString();
            return s != null ? s + currentField.getName() + "[" + key + "]" : currentField.getName() + "[" + key + "]";
        }
    }

    static class DirectArrayEntryInterpolatable
        extends NuElement
        implements Leaf
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

        public String getOriginalValue()
        {
            return originalValue;
        }

        public void setValue( Object root, String value )
            throws IllegalAccessException
        {
            Array.set( parent.getValue( root ), pos, value );
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
            return parent.toString();
        }
    }


    static class ListItemInterpolatable
        extends FieldBoundNuElement
        implements Leaf
    {
        private final int pos;

        private final String originalValue;

        ListItemInterpolatable( NuElement parent, Field currentField, int pos, String originalValue )
        {
            super( parent, currentField );
            this.pos = pos;
            this.originalValue = originalValue;
        }

        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            return currentField.get( parent.getValue( root ) );
        }

        public String getOriginalValue()
        {
            return originalValue;
        }

        public void setValue( Object root, String value )
            throws IllegalAccessException
        {
            @SuppressWarnings("unchecked") List<Object> list = (List) getValue( root );
            try
            {
                list.set( pos, value );
            }
            catch ( java.lang.UnsupportedOperationException ignore )
            {
            }
        }

        @Override
        public String toString()
        {
            return parent.toString() + "." + currentField.getName() + "[" + pos + "]";
        }
    }

    static class StringMemberInterpolatable
        extends FieldBoundNuElement
        implements Leaf
    {
        private final String originalValue;

        StringMemberInterpolatable( NuElement parent, Field currentField, String originalValue )
        {
            super( parent, currentField );
            this.originalValue = originalValue;
        }


        public String getOriginalValue()
        {
            return originalValue;
        }

        public void setValue( Object root, String value )
            throws IllegalAccessException
        {
            Object obj = parent.getValue( root );
            currentField.set( obj, value );
        }

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
            return Array.get( parent.getValue( root ), pos );
        }


        @Override
        public String toString()
        {
            return parent.toString() + "[" + pos + "]";
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

            List list = (List) currentField.get( parent.getValue( target ) );
            return list.get( pos );
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

        ObjectContainer( Field currentField, NuElement parent )
        {
            this.currentField = currentField;
            this.parent = parent;
        }


        @Override
        public Object getValue( Object root )
            throws IllegalAccessException
        {
            return currentField.get( parent.getValue( root ) );
        }

        @Override
        public String toString()
        {
            String s = parent.toString();
            return s != null ? s + "." + currentField.getName() : currentField.getName();
        }
    }


    class Elements
    {
        private final Iterable<Leaf> elements;

        Elements( Iterable<Leaf> element )
        {
            this.elements = element;
        }

        public void interpolate( Object root, Interpolator interpolator )
            throws IllegalAccessException
        {
            for ( Leaf element : elements )
            {
                String originalValue = element.getOriginalValue();
                String s = interpolator.interpolate( originalValue );
                if ( originalValue != null && !originalValue.equals( s ) )
                {
                    element.setValue( root, s );
                }
            }
        }

        public String toString()
        {
            StringBuilder result = new StringBuilder();
            for ( Leaf element : elements )
            {
                result.append( element.toString() );
                result.append( "\n" );
            }
            return result.toString();
        }

    }

}
