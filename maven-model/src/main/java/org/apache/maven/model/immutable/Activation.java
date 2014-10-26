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

public class Activation
{
    public final boolean activeByDefault;
    public final String jdk;
    public final ActivationOS os;
    public final ActivationProperty property;
    public final ActivationFile file;
    public final java.util.Map<Object, InputLocation> locations;

    Activation( Boolean activeByDefault, String jdk, ActivationOS os, ActivationProperty property,
                        ActivationFile file, Map<Object, InputLocation> locations )
    {
        this.activeByDefault = activeByDefault != null ? activeByDefault : false;
        this.jdk = jdk;
        this.os = os;
        this.property = property;
        this.file = file;
        this.locations = locations;
    }

}
