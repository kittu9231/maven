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

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Information about one of the committers on this project.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Developer
    extends Contributor
    implements java.io.Serializable
{

    public final String id;

    Developer( int hashCode, String name, String email, String url, String organization, String organizationUrl,
                       List<String> roles, String timezone, Properties properties, Map<Object, InputLocation> locations,
                       String id )
    {
        super( hashCode, name, email, url, organization, organizationUrl, roles, timezone, properties, locations );
        this.id = id != null ? id.intern() : null;
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
        if ( !super.equals( o ) )
        {
            return false;
        }

        Developer developer = (Developer) o;

        if ( id != null ? !id.equals( developer.id ) : developer.id != null )
        {
            return false;
        }

        return true;
    }


    public static int hashCode( String id, String name, String email, String url, String organization, String organizationUrl,
                                List<String> roles, String timezone, Properties properties )
    {

        int result = 0;
        result = 31 * result + ( id != null ? id.hashCode() : 0 );
        int hc =
            Contributor.hashCode( name, email, url, organization, organizationUrl, roles, timezone, properties );
        result = 31 * result +hc ;
        return result;
    }

}
