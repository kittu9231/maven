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
 * Describes a build extension to utilise.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Extension
{
    private String groupId;
    private String artifactId;
    private String version;
    private java.util.Map<Object, InputLocation> locations;

    Extension( String groupId, String artifactId, String version, Map<Object, InputLocation> locations )
    {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.locations = locations;
    }

}
