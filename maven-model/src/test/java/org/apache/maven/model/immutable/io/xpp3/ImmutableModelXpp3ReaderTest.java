package org.apache.maven.model.immutable.io.xpp3;


import junit.framework.TestCase;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;

public class ImmutableModelXpp3ReaderTest extends TestCase
{

    public void testOldstyle()
        throws Exception
    {
        doItOldStyle();
    }
    public void testnewstyle()
        throws Exception
    {
        readNewStyle();
    }

    public void testOldstyle2()
        throws Exception
    {
        doItOldStyle();
    }
    public void testnewstyle2()
        throws Exception
    {
        readNewStyle();
    }
    public void doItOldStyle()
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

    public void readNewStyle()
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