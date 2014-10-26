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

public class ConfigurationContainer
{
    public final String inherited;
    public final Object configuration;
    public final Map<Object, InputLocation> locations;

    ConfigurationContainer( String inherited, Object configuration, Map<Object, InputLocation> locations )
    {
        this.inherited = inherited;
        this.configuration = configuration;
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

        ConfigurationContainer that = (ConfigurationContainer) o;

        if ( configuration != null ? !configuration.equals( that.configuration ) : that.configuration != null )
        {
            return false;
        }
        if ( inherited != null ? !inherited.equals( that.inherited ) : that.inherited != null )
        {
            return false;
        }
        if ( locations != null ? !locations.equals( that.locations ) : that.locations != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = inherited != null ? inherited.hashCode() : 0;
        result = 31 * result + ( configuration != null ? configuration.hashCode() : 0 );
        result = 31 * result + ( locations != null ? locations.hashCode() : 0 );
        return result;
    }
}
