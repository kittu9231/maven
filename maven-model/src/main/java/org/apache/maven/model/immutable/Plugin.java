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

import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.util.List;
import java.util.Map;

/**
 * 
 *         
 *         The <code>&lt;plugin&gt;</code> element contains
 * informations required for a plugin.
 *         
 *       
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Plugin
    extends ConfigurationContainer
{
    private String groupId;
    private String artifactId;
    private String version;
    private String extensions;
    private java.util.List<PluginExecution> executions;
    private java.util.List<Dependency> dependencies;
    private Object goals;

    Plugin( String inherited, Object configuration, Map<Object, InputLocation> locations, String groupId,
                    String artifactId, String version, String extensions, List<PluginExecution> executions,
                    List<Dependency> dependencies, Xpp3Dom goals )
    {
        super( inherited, configuration, locations );
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.extensions = extensions;
        this.executions = executions;
        this.dependencies = dependencies;
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

        Plugin plugin = (Plugin) o;

        if ( artifactId != null ? !artifactId.equals( plugin.artifactId ) : plugin.artifactId != null )
        {
            return false;
        }
        if ( dependencies != null ? !dependencies.equals( plugin.dependencies ) : plugin.dependencies != null )
        {
            return false;
        }
        if ( executions != null ? !executions.equals( plugin.executions ) : plugin.executions != null )
        {
            return false;
        }
        if ( extensions != null ? !extensions.equals( plugin.extensions ) : plugin.extensions != null )
        {
            return false;
        }
        if ( goals != null ? !goals.equals( plugin.goals ) : plugin.goals != null )
        {
            return false;
        }
        if ( groupId != null ? !groupId.equals( plugin.groupId ) : plugin.groupId != null )
        {
            return false;
        }
        if ( version != null ? !version.equals( plugin.version ) : plugin.version != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + ( groupId != null ? groupId.hashCode() : 0 );
        result = 31 * result + ( artifactId != null ? artifactId.hashCode() : 0 );
        result = 31 * result + ( version != null ? version.hashCode() : 0 );
        result = 31 * result + ( extensions != null ? extensions.hashCode() : 0 );
        result = 31 * result + ( executions != null ? executions.hashCode() : 0 );
        result = 31 * result + ( dependencies != null ? dependencies.hashCode() : 0 );
        result = 31 * result + ( goals != null ? goals.hashCode() : 0 );
        return result;
    }
}
