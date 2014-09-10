/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.maven;

import junit.framework.TestCase;
import org.apache.maven.model.Dependency;
import org.apache.maven.project.*;
import org.apache.maven.project.DuplicateProjectException;
import org.codehaus.plexus.util.dag.CycleDetectedException;

import java.util.Arrays;
import java.util.List;

public class DefaultProjectDependencyGraphTest
    extends TestCase
{

    private final MavenProject A = createA();


    private final MavenProject depender1 = createC(Arrays.asList( toDependency( A)), "depender1" );

    private final MavenProject depender2 = createC(Arrays.asList( toDependency( A )), "depender2" );

    private final MavenProject depender3 = createC(Arrays.asList( toDependency( A )), "depender3" );

    public void testGetSortedProjects()
        throws Exception
    {
        DefaultProjectDependencyGraph dd = new DefaultProjectDependencyGraph( Arrays.asList( depender1, A ) );
        final List<MavenProject> sortedProjects = dd.getSortedProjects();
        assertEquals( A, sortedProjects.get(0));
        assertEquals( depender1, sortedProjects.get(1));
    }

    public void testVerifyExpectedParentStructure()
        throws CycleDetectedException, DuplicateProjectException
    {
        // This test verifies the baseline structure used in susequent tests. If this fails, the rest will fail.
        DefaultProjectDependencyGraph dd = aParentWith3Children();
        final List<MavenProject> sortedProjects = dd.getSortedProjects();
        assertEquals( A, sortedProjects.get(0));
        assertEquals( depender1, sortedProjects.get(1));
        assertEquals( depender2, sortedProjects.get(2));
        assertEquals( depender3, sortedProjects.get(3));
    }

    public void testVerifyThatDownsteamProjectsComeInSortedOrder()
        throws CycleDetectedException, DuplicateProjectException
    {
        final List<MavenProject> downstreamProjects = aParentWith3Children().getDownstreamProjects( A, true );
        assertEquals( depender1, downstreamProjects.get(0));
        assertEquals( depender2, downstreamProjects.get(1));
        assertEquals( depender3, downstreamProjects.get(2));
    }


    public void testGetUpstreamProjects()
        throws CycleDetectedException, DuplicateProjectException
    {
        DefaultProjectDependencyGraph dd = aParentWith3Children();
        final List<MavenProject> downstreamProjects = dd.getUpstreamProjects( depender1, true );
        assertEquals( A, downstreamProjects.get(0));
    }

    private DefaultProjectDependencyGraph aParentWith3Children()
        throws CycleDetectedException, org.apache.maven.project.DuplicateProjectException
    {
        return new DefaultProjectDependencyGraph( Arrays.asList( depender1, depender2, depender3, A ) );
    }

    private static MavenProject createA()
    {
        MavenProject A = new MavenProject();
        A.setGroupId( "org.apache" );
        A.setArtifactId( "A" );
        A.setVersion( "1.2" );
        return A;
    }

    Dependency toDependency(MavenProject mavenProject){
        final Dependency dependency = new Dependency();
        dependency.setArtifactId( mavenProject.getArtifactId() );
        dependency.setGroupId( mavenProject.getGroupId() );
        dependency.setVersion( mavenProject.getVersion() );
        return dependency;
    }

    private MavenProject createC( List<Dependency> dependencies, String artifactId )
    {
        MavenProject result = new MavenProject();
        result.setGroupId( "org.apache" );
        result.setArtifactId( artifactId );
        result.setVersion( "1.2" );
        result.setDependencies( dependencies );
        return result;
    }

}