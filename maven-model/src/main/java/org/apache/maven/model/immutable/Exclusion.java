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
 * 
 *         
 *         The <code>&lt;exclusion&gt;</code> element contains
 * informations required to exclude
 *         an artifact to the project.
 *         
 *       
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Exclusion extends FixedHashCode
{
    private String artifactId;

    private String groupId;

    private java.util.Map<Object, InputLocation> locations;

    Exclusion( int hashCode, String artifactId, String groupId, Map<Object, InputLocation> locations )
    {
        super(hashCode);
        this.artifactId = artifactId;
        this.groupId = groupId;
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

        Exclusion exclusion = (Exclusion) o;

        if ( artifactId != null ? !artifactId.equals( exclusion.artifactId ) : exclusion.artifactId != null )
        {
            return false;
        }
        if ( groupId != null ? !groupId.equals( exclusion.groupId ) : exclusion.groupId != null )
        {
            return false;
        }

        return true;
    }

    public static int hashCode( String artifactId1, String groupId1 )
    {
        int result = artifactId1 != null ? artifactId1.hashCode() : 0;
        result = 31 * result + ( groupId1 != null ? groupId1.hashCode() : 0 );
        return result;
    }
}
