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
 *         The <code>&lt;parent&gt;</code> element contains
 * information required to locate the parent project from which
 *         this project will inherit from.
 *         <strong>Note:</strong> The children of this element are
 * not interpolated and must be given as literal values.
 *         
 *       
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Parent extends FixedHashCode
{

    private String groupId;
    private String artifactId;
    private String version;
    private String relativePath = "../pom.xml";
    private java.util.Map<Object, InputLocation> locations;

    Parent( int hc, String groupId, String artifactId, String version, String relativePath,
                    Map<Object, InputLocation> locations )
    {
        super(hc);
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.relativePath = relativePath;
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

        Parent parent = (Parent) o;

        if ( artifactId != null ? !artifactId.equals( parent.artifactId ) : parent.artifactId != null )
        {
            return false;
        }
        if ( groupId != null ? !groupId.equals( parent.groupId ) : parent.groupId != null )
        {
            return false;
        }
        if ( relativePath != null ? !relativePath.equals( parent.relativePath ) : parent.relativePath != null )
        {
            return false;
        }
        if ( version != null ? !version.equals( parent.version ) : parent.version != null )
        {
            return false;
        }

        return true;
    }

    public static int hashCode( String groupId1, String artifactId1, String version1, String relativePath1 )
    {
        int result = groupId1 != null ? groupId1.hashCode() : 0;
        result = 31 * result + ( artifactId1 != null ? artifactId1.hashCode() : 0 );
        result = 31 * result + ( version1 != null ? version1.hashCode() : 0 );
        result = 31 * result + ( relativePath1 != null ? relativePath1.hashCode() : 0 );
        return result;
    }
}
