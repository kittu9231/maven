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
 * Describes the licenses for this project. This is used to
 * generate the license
 *         page of the project's web site, as well as being taken
 * into consideration in other reporting
 *         and validation. The licenses listed for the project are
 * that of the project itself, and not
 *         of dependencies.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class License extends FixedHashCode
{
    private String name;
    private String url;
    private String distribution;
    private String comments;
    private java.util.Map<Object, InputLocation> locations;

    License( String name, String url, String distribution, String comments,
                     Map<Object, InputLocation> locations )
    {
        super(hashCode( name, url, distribution, comments ));
        this.name = name;
        this.url = url;
        this.distribution = distribution;
        this.comments = comments;
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

        License license = (License) o;

        if ( comments != null ? !comments.equals( license.comments ) : license.comments != null )
        {
            return false;
        }
        if ( distribution != null ? !distribution.equals( license.distribution ) : license.distribution != null )
        {
            return false;
        }
        if ( name != null ? !name.equals( license.name ) : license.name != null )
        {
            return false;
        }
        if ( url != null ? !url.equals( license.url ) : license.url != null )
        {
            return false;
        }

        return true;
    }

    public static int hashCode( String name1, String url1, String distribution1, String comments1 )
    {
        int result = name1 != null ? name1.hashCode() : 0;
        result = 31 * result + ( url1 != null ? url1.hashCode() : 0 );
        result = 31 * result + ( distribution1 != null ? distribution1.hashCode() : 0 );
        result = 31 * result + ( comments1 != null ? comments1.hashCode() : 0 );
        return result;
    }
}
