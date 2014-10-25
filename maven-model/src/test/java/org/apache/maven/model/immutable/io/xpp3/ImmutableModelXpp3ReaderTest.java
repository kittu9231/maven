package org.apache.maven.model.immutable.io.xpp3;


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