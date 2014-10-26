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

/**
 * Definition of include or exclude patterns.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class PatternSet extends FixedHashCode
{
    private java.util.List<String> includes;
    private java.util.List<String> excludes;
    private java.util.Map<Object, InputLocation> locations;

    PatternSet( int hashCode, List<String> includes, List<String> excludes, Map<Object, InputLocation> locations )
    {
        super( hashCode );
        this.includes = includes;
        this.excludes = excludes;
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

        PatternSet that = (PatternSet) o;

        if ( excludes != null ? !excludes.equals( that.excludes ) : that.excludes != null )
        {
            return false;
        }
        if ( includes != null ? !includes.equals( that.includes ) : that.includes != null )
        {
            return false;
        }

        return true;
    }

    public static int hashCode( List<String> includes1, List<String> excludes1 )
    {
        int result = includes1 != null ? includes1.hashCode() : 0;
        result = 31 * result + ( excludes1 != null ? excludes1.hashCode() : 0 );
        return result;
    }
}
