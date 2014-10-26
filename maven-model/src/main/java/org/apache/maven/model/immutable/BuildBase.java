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

public class BuildBase
    extends PluginConfiguration
{
    public final String defaultGoal;
    public final java.util.List<Resource> resources;
    public final java.util.List<Resource> testResources;
    public final String directory;
    public final String finalName;
    public final java.util.List<String> filters;

    BuildBase( List<Plugin> plugins, Map<Object, InputLocation> locations, PluginManagement pluginManagement,
               String defaultGoal, List<Resource> resources, List<Resource> testResources, String directory,
               String finalName, List<String> filters )
    {
        super( plugins, locations, pluginManagement );
        this.defaultGoal = defaultGoal;
        this.resources = resources;
        this.testResources = testResources;
        this.directory = directory;
        this.finalName = finalName;
        this.filters = filters;
    }

}
