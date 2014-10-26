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
 * This element describes all of the classpath resources associated
 * with a project
 *         or unit tests.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Resource
    extends FileSet
{
    public final String targetPath;
    public final String filtering;

    Resource( List<String> includes, List<String> excludes, Map<Object, InputLocation> locations,
                      String directory, String targetPath, String filtering )
    {
        super( includes, excludes, locations, directory );
        this.targetPath = targetPath;
        this.filtering = filtering;
    }

}
