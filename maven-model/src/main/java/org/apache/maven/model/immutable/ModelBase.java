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
import java.util.Properties;

/**
 * 
 *         
 *         Base class for the <code>Model</code> and the
 * <code>Profile</code> objects.
 *         
 *       
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class ModelBase
{
    private java.util.List<String> modules;
    private DistributionManagement distributionManagement;
    private java.util.Properties properties;
    private DependencyManagement dependencyManagement;
    private java.util.List<Dependency> dependencies;
    private java.util.List<Repository> repositories;
    private java.util.List<Repository> pluginRepositories;
    private Object reports;
    private Reporting reporting;
    private java.util.Map<Object, InputLocation> locations;

    ModelBase( List<String> modules, DistributionManagement distributionManagement, Properties properties,
               DependencyManagement dependencyManagement, List<Dependency> dependencies, List<Repository> repositories,
               List<Repository> pluginRepositories, Object reports, Reporting reporting,
               Map<Object, InputLocation> locations )
    {
        this.modules = modules;
        this.distributionManagement = distributionManagement;
        this.properties = properties;
        this.dependencyManagement = dependencyManagement;
        this.dependencies = dependencies;
        this.repositories = repositories;
        this.pluginRepositories = pluginRepositories;
        this.reports = reports;
        this.reporting = reporting;
        this.locations = locations;
    }

}
