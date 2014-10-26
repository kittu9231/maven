/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package org.apache.maven.model.immutable;

import java.util.Map;

/**
 * A repository contains the information needed for establishing
 * connections with
 *         remote repository.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class RepositoryBase extends FixedHashCode
{
    private final String id;
    private final String name;
    private final String url;
    private final String layout;
    private final java.util.Map<Object, InputLocation> locations;

    RepositoryBase( int hashCode, String id, String name, String url, String layout, Map<Object, InputLocation> locations )
    {
        super( hashCode );
        this.id = internNullable( id);
        this.name = internNullable( name);
        this.url = internNullable( url);
        this.layout = internNullable( layout);
        this.locations = locations;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        RepositoryBase that = (RepositoryBase) o;

        if ( id != null ? !id.equals( that.id ) : that.id != null )
        {
            return false;
        }
        if ( layout != null ? !layout.equals( that.layout ) : that.layout != null )
        {
            return false;
        }
        if ( name != null ? !name.equals( that.name ) : that.name != null )
        {
            return false;
        }
        if ( url != null ? !url.equals( that.url ) : that.url != null )
        {
            return false;
        }

        return true;
    }

    public static int hashCode( String id, String name1, String url1, String layout1 )
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + ( name1 != null ? name1.hashCode() : 0 );
        result = 31 * result + ( url1 != null ? url1.hashCode() : 0 );
        result = 31 * result + ( layout1 != null ? layout1.hashCode() : 0 );
        return result;
    }
}
