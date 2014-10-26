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

public class Site
{
    public final String id;
    public final String name;
    public final String url;
    public final java.util.Map<Object, InputLocation> locations;

    Site( String id, String name, String url, Map<Object, InputLocation> locations )
    {
        this.id = id;
        this.name = name;
        this.url = url;
        this.locations = locations;
    }
}
