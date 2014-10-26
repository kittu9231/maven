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
 *         The <code>&lt;build&gt;</code> element contains
 * informations required to build the project.
 *         
 *       
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Build
    extends BuildBase
{

    private String sourceDirectory;
    private String scriptSourceDirectory;
    private String testSourceDirectory;
    private String outputDirectory;
    private String testOutputDirectory;
    private java.util.List<Extension> extensions;

    Build( List<Plugin> plugins, Map<Object, InputLocation> locations, PluginManagement pluginManagement,
                   String defaultGoal, List<Resource> resources, List<Resource> testResources, String directory,
                   String finalName, List<String> filters, String sourceDirectory, String scriptSourceDirectory,
                   String testSourceDirectory, String outputDirectory, String testOutputDirectory,
                   List<Extension> extensions )
    {
        super( plugins, locations, pluginManagement, defaultGoal, resources, testResources, directory, finalName,
               filters );
        this.sourceDirectory = sourceDirectory;
        this.scriptSourceDirectory = scriptSourceDirectory;
        this.testSourceDirectory = testSourceDirectory;
        this.outputDirectory = outputDirectory;
        this.testOutputDirectory = testOutputDirectory;
        this.extensions = extensions;
    }

}
