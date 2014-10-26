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
 * This elements describes all that pertains to distribution for a
 * project. It is
 *         primarily used for deployment of artifacts and the site
 * produced by the build.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class DistributionManagement
{
    private DeploymentRepository repository;
    private DeploymentRepository snapshotRepository;
    private Site site;
    private String downloadUrl;
    private Relocation relocation;
    private String status;
    private java.util.Map<Object, InputLocation> locations;

    DistributionManagement( DeploymentRepository repository, DeploymentRepository snapshotRepository, Site site,
                                    String downloadUrl, Relocation relocation, String status,
                                    Map<Object, InputLocation> locations )
    {
        this.repository = repository;
        this.snapshotRepository = snapshotRepository;
        this.site = site;
        this.downloadUrl = downloadUrl;
        this.relocation = relocation;
        this.status = status;
        this.locations = locations;
    }

}
