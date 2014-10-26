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
 * A repository contains the information needed for establishing
 * connections with
 *         remote repository.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings( "all" )
public class Repository
    extends RepositoryBase
{

    public final RepositoryPolicy releases;
    public final RepositoryPolicy snapshots;

    Repository( String id, String name, String url, String layout, Map<Object, InputLocation> locations,
                RepositoryPolicy releases, RepositoryPolicy snapshots )
    {
        super( hashCode( id, name, url, layout, releases, snapshots ), id, name, url, layout, locations );
        this.releases = releases;
        this.snapshots = snapshots;
    }

    public static int hashCode( String id, String name1, String url1, String layout1, RepositoryPolicy releases,
                                RepositoryPolicy snapshots)
    {
        int result = hashCode( id, name1, url1, layout1 );
        result = 31 * result + ( releases != null ? releases.hashCode( releases.enabled, releases.updatePolicy,
                                                                       releases.checksumPolicy ) : 0 );
        result = 31 * result + ( snapshots != null ? snapshots.hashCode( snapshots.enabled, snapshots.updatePolicy,
                                                                         snapshots.checksumPolicy ) : 0 );
        return result;
    }


}
