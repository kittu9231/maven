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
 * Describes the prerequisites a project can have.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Prerequisites extends FixedHashCode
{
   private String maven = "2.0";
    private java.util.Map<Object, InputLocation> locations;

    Prerequisites( String maven, Map<Object, InputLocation> locations )
    {
        super( hashCode( maven ));
        this.maven = maven;
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

        Prerequisites that = (Prerequisites) o;

        if ( maven != null ? !maven.equals( that.maven ) : that.maven != null )
        {
            return false;
        }

        return true;
    }

    public static int hashCode( String maven1 )
    {
        return maven1 != null ? maven1.hashCode() : 0;
    }
}
