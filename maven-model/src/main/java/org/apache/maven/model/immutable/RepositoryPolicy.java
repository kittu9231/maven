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

public class RepositoryPolicy extends FixedHashCode
{
    public final String enabled;
    public final String updatePolicy;
    public final String checksumPolicy;
    public final Map<Object, InputLocation> locations;

    RepositoryPolicy( String enabled, String updatePolicy, String checksumPolicy,
                              Map<Object, InputLocation> locations )
    {
        super( hashCode( enabled, updatePolicy, checksumPolicy ));
        this.enabled = enabled;
        this.updatePolicy = updatePolicy;
        this.checksumPolicy = checksumPolicy;
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

        RepositoryPolicy that = (RepositoryPolicy) o;

        if ( checksumPolicy != null ? !checksumPolicy.equals( that.checksumPolicy ) : that.checksumPolicy != null )
        {
            return false;
        }
        if ( enabled != null ? !enabled.equals( that.enabled ) : that.enabled != null )
        {
            return false;
        }
        if ( updatePolicy != null ? !updatePolicy.equals( that.updatePolicy ) : that.updatePolicy != null )
        {
            return false;
        }

        return true;
    }

    public static int hashCode( String enabled1, String updatePolicy1, String checksumPolicy1 )
    {
        int result = enabled1 != null ? enabled1.hashCode() : 0;
        result = 31 * result + ( updatePolicy1 != null ? updatePolicy1.hashCode() : 0 );
        result = 31 * result + ( checksumPolicy1 != null ? checksumPolicy1.hashCode() : 0 );
        return result;
    }
}
