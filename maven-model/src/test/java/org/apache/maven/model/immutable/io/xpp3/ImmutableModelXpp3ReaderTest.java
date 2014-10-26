package org.apache.maven.model.immutable.io.xpp3;
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



import junit.framework.TestCase;
import org.apache.maven.model.immutable.ImmutableModelBuilder;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;

public class ImmutableModelXpp3ReaderTest extends TestCase
{

    public void testImmutable()
        throws Exception
    {
        immutable();
    }
    public void testMutabke()
        throws Exception
    {
        mutable();
    }

    public void testImmutable2()
        throws Exception
    {
        immutable();
    }
    public void testMutable2()
        throws Exception
    {
        mutable();
    }
    public void immutable()
        throws Exception
    {

        File basedir = getBaseDir();
        String[] includedFiles = getIncludedFiles( basedir );
        ImmutableModelXpp3Reader reader = new ImmutableModelXpp3Reader();
        for ( String includedFile : includedFiles )
        {
            File file = new File( basedir, includedFile );
            try
            {
                reader.read( new FileReader( file ), false );
            } catch (XmlPullParserException e){
                System.out.println( "file = " + file );
                throw e;
            }

        }
        ImmutableModelBuilder builder = reader.getBuilder();
        assertNotNull( builder );

    }

    public void mutable()
        throws Exception
    {

        File basedir = getBaseDir();
        String[] includedFiles = getIncludedFiles( basedir );
        MavenXpp3Reader reader = new MavenXpp3Reader();
        for ( String includedFile : includedFiles )
        {
            File file = new File( basedir, includedFile );
            try
            {
                reader.read( new FileReader( file ), false );
            } catch (XmlPullParserException e){
                System.out.println( "file = " + file );
                throw e;
            }

        }

    }

    private String[] getIncludedFiles( File basedir )
    {
        DirectoryScanner ds = new DirectoryScanner();
        ds.setIncludes( new String[]{"org/codehaus/**/*.pom"} );
        ds.setBasedir( basedir );
        ds.scan();
        return ds.getIncludedFiles();
    }

    private File getBaseDir()
    {
        String property = System.getProperty( "user.home" );
        return new File( property, ".m2/repository" );
    }
}