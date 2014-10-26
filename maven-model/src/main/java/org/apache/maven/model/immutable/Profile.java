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
import java.util.Properties;

public class Profile
    extends ModelBase
{
    public final String id;
    public final  Activation activation;
    public final BuildBase build;

    Profile( List<String> modules, DistributionManagement distributionManagement, Properties properties,
                     DependencyManagement dependencyManagement, List<Dependency> dependencies,
                     List<Repository> repositories, List<Repository> pluginRepositories, Xpp3Dom reports,
                     Reporting reporting, Map<Object, InputLocation> locations, String id, Activation activation,
                     BuildBase build )
    {
        super( modules, distributionManagement, properties, dependencyManagement, dependencies, repositories,
               pluginRepositories, reports, reporting, locations );
        this.id = id;
        this.activation = activation;
        this.build = build;
    }

}
