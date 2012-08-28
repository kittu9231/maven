package org.apache.maven.model.interpolation;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InterpolationCache
{

    private static final Map<Class<?>, CacheItem> cachedEntries =
        new ConcurrentHashMap<Class<?>, CacheItem>( 80, 0.75f, 2 );
    // Empirical data from 3.x, actual =40

    public static void clearCaches()
    {
        cachedEntries.clear();
    }


    public static CacheItem getCacheEntry( Class<?> cls )
    {
        if (cls == null) return null;
        CacheItem cacheItem = cachedEntries.get( cls );
        if ( cacheItem == null )
        {

            CacheItem parent = getCacheEntry( cls.getSuperclass() );
            cacheItem = new CacheItem( cls, parent );
            cachedEntries.put( cls, cacheItem );
        }
        return cacheItem;
    }

    public static class CacheItem
    {
        public final boolean isArray;

        public final boolean isQualifiedForInterpolation;

        public final CacheField[] fields;

        @SuppressWarnings( "SimplifiableIfStatement" )
        private boolean isQualifiedForInterpolation( Field field, Class<?> fieldType )
        {
            if ( Map.class.equals( fieldType ) && "locations".equals( field.getName() ) )
            {
                return false;
            }

            if ( fieldType.isPrimitive() )
            {
                return false;
            }

            return !"parent".equals( field.getName() );
        }


        public CacheField findField(String name){
            for ( CacheField field : fields )
            {
                if (name.equals( field.field.getName() )){
                    return field;
                }
            }
            return null;
        }

        CacheItem( Class clazz, CacheItem parent )
        {
            this.isQualifiedForInterpolation = isQualifiedForInterpolation( clazz );
            this.isArray = clazz.isArray();
            List<CacheField> fields = new ArrayList<CacheField>();
            if ( isQualifiedForInterpolation )
            {
                if ( parent != null )
                {
                    Collections.addAll( fields, parent.fields );
                }
                for ( Field currentField : clazz.getDeclaredFields() )
                {
                    Class<?> type = currentField.getType();
                    if ( isQualifiedForInterpolation( currentField, type ) )
                    {
                        if ( String.class == type )
                        {
                            if ( !Modifier.isFinal( currentField.getModifiers() ) )
                            {
                                fields.add( new CacheField( currentField, true, false, false, false, false ) );
                            }
                        }
                        else if ( List.class.isAssignableFrom( type ) )
                        {
                            fields.add( new CacheField( currentField, false, true, false, false, false ) );
                        }
                        else if ( Collection.class.isAssignableFrom( type ) )
                        {
                            fields.add( new CacheField( currentField, false, false, true, false, false ) );
                        }
                        else if ( Map.class.isAssignableFrom( type ) )
                        {
                            fields.add( new CacheField( currentField, false, false, false, true, false ) );
                        }
                        else
                        {
                            fields.add( new CacheField( currentField, false, false, false, false, true ) );
                        }
                    }

                }
            }
            this.fields = fields.toArray( new CacheField[fields.size()] );
        }

        private boolean isQualifiedForInterpolation( Class<?> cls )
        {
            return !cls.getName().startsWith( "java" );
        }


    }

    static class CacheField
    {
        public final Field field;

        public final Type type;

        public final boolean isString;

        public final boolean isList;

        public final boolean isColletin;

        public final boolean isMap;

        public final boolean isObject;

        CacheField( Field field, boolean string, boolean list, boolean isCollection, boolean map, boolean object )
        {
            this.field = field;
            this.type = field.getType();
            isString = string;
            isList = list;
            isColletin = isCollection;
            isMap = map;
            isObject = object;
        }

        public String toString(){
            return field.getName();
        }
    }
}
