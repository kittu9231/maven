package org.apache.maven.model.immutable;

import org.apache.maven.model.immutable.Developer;
import org.apache.maven.model.immutable.Contributor;
import org.apache.maven.model.immutable.Dependency;
import org.apache.maven.model.immutable.Exclusion;
import org.apache.maven.model.immutable.InputLocation;
import org.apache.maven.model.immutable.PatternSet;
import org.apache.maven.model.immutable.Plugin;
import org.apache.maven.model.immutable.PluginExecution;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings( "MethodWithTooManyParameters" )
public class ImmutableModelBuilder
{


    private static class HashArray<T> {
        private final Map<Integer,ArrayList<T>> map =new HashMap<Integer, ArrayList<T>>(  );

        ArrayList<T> getIt(int hashCode){
            ArrayList<T> result = map.get( hashCode );
            if (result == null){
                result = new ArrayList<T>(  );
                map.put( hashCode, result );
            }
            return result;
        }
    }


    private static class WeirdMap<T> {
        private final Map<T,T> map =new HashMap<T, T>(  );

        int hits;
        T getIt(T t){
            T result = map.get( t );
            if (result == null){
                result = t;
                map.put( t, t );
            } else {
                hits++;
            }
            return result;
        }
    }

    private final WeirdMap<Dependency> deps = new WeirdMap<Dependency>(  );
    private final WeirdMap<Exclusion> exclusions = new WeirdMap<Exclusion>(  );
    private final WeirdMap<Plugin> plugin = new WeirdMap<Plugin>(  );
    private final WeirdMap<PatternSet> patternSets = new WeirdMap<PatternSet>(  );
    private final HashArray<Developer> developers = new HashArray<Developer>();
    private final HashArray<Contributor> contributors = new HashArray<Contributor>();

    public Developer createDeveloper( String name, String email, String url, String organization,
                                             String organizationUrl, List<String> roles, String timezone,
                                             Properties properties, Map<Object, InputLocation> locations, String id )
    {
        int hc =
            Developer.hashCode( id, name, email, url, organization, organizationUrl, roles, timezone, properties );
        ArrayList<org.apache.maven.model.immutable.Developer> it = developers.getIt( hc );
        Developer c =
            new Developer(  hc, name, email, url, organization, organizationUrl, roles, timezone, properties, locations, id );

        for ( Developer contributor : it )
        {
            if (contributor.equals( c )) return contributor;
        }

        it.add( c);
        return c;
    }


    public Contributor createContributor( String name, String email, String url, String organization,
                                          String organizationUrl, List<String> roles, String timezone,
                                          Properties properties, Map<Object, InputLocation> locations )
    {
        int hc =
             Contributor.hashCode( name, email, url, organization, organizationUrl, roles, timezone, properties );

        ArrayList<Contributor> it = contributors.getIt( hc );
        for ( Contributor contributor : it )
        {
            if (Contributor.equals( contributor,email,  name,  organization, organizationUrl, properties,roles, timezone, url ))
                return contributor;
        }
        Contributor contributor =
            new Contributor( hc, name, email, url, organization, organizationUrl, roles, timezone, properties, locations );
        it.add( contributor);
        return contributor;
    }

    public Dependency createDependency( String groupId, String artifactId, String version, String type,
                                               String classifier, String scope, String systemPath,
                                               List<Exclusion> exclusions, String optional,
                                               Map<Object, InputLocation> locations )
    {
        return deps.getIt(
            new Dependency( groupId, artifactId, version, type, classifier, scope, systemPath, exclusions, optional,
                            locations ));
    }

    public Exclusion createExclusion( String artifactId, String groupId, Map<Object, InputLocation> locations )
    {
        return exclusions.getIt( new Exclusion( artifactId, groupId, locations ) );
    }


    public Plugin createPlugin( String inherited, Object configuration, Map<Object, InputLocation> locations,
                                       String groupId, String artifactId, String version, String extensions,
                                       List<PluginExecution> executions, List<Dependency> dependencies, Xpp3Dom goals )
    {
        Plugin plugin1 =
            new Plugin( inherited, configuration, locations, groupId, artifactId, version, extensions, executions,
                        dependencies, goals );
        return plugin.getIt( plugin1 );
    }

    public PatternSet createPatternSet( List<String> includes, List<String> excludes, Map<Object, InputLocation> locations )
    {
        PatternSet patternSet = new PatternSet( includes, excludes, locations );
        return patternSets.getIt( patternSet );
    }

}
