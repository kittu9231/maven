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
 * 
 *         
 *         The <code>&lt;execution&gt;</code> element contains
 * informations required for the
 *         execution of a plugin.
 *         
 *       
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class PluginExecution
    extends ConfigurationContainer
{

    public final String id;
    public final  String phase;
    public final java.util.List<String> goals;

    PluginExecution( String inherited, Object configuration, Map<Object, InputLocation> locations, String id,
                             String phase, List<String> goals )
    {
        super( inherited, configuration, locations );
        this.id = id;
        this.phase = phase;
        this.goals = goals;
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

        PluginExecution that = (PluginExecution) o;

        if ( goals != null ? !goals.equals( that.goals ) : that.goals != null )
        {
            return false;
        }
        if ( id != null ? !id.equals( that.id ) : that.id != null )
        {
            return false;
        }
        if ( phase != null ? !phase.equals( that.phase ) : that.phase != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + ( id != null ? id.hashCode() : 0 );
        result = 31 * result + ( phase != null ? phase.hashCode() : 0 );
        result = 31 * result + ( goals != null ? goals.hashCode() : 0 );
        return result;
    }
}
