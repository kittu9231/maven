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
 * This is the file specification used to activate the profile. The
 * <code>missing</code> value
 *         is the location of a file that needs to exist, and if it
 * doesn't, the profile will be
 *         activated. On the other hand, <code>exists</code> will
 * test for the existence of the file and if it is
 *         there, the profile will be activated.<br/>
 *         Variable interpolation for these file specifications is
 * limited to <code>${basedir}</code>,
 *         System properties and request properties.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class ActivationFile
{
    private String missing;
    private String exists;
    private java.util.Map<Object, InputLocation> locations;

    ActivationFile( String missing, String exists, Map<Object, InputLocation> locations )
    {
        this.missing = missing;
        this.exists = exists;
        this.locations = locations;
    }

}
