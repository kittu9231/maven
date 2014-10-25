package org.apache.maven.model.immutable.io.xpp3;

import org.apache.maven.model.immutable.Dependency;
import org.apache.maven.model.immutable.Developer;
import org.apache.maven.model.immutable.Exclusion;
import org.apache.maven.model.immutable.InputLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImmutableModelBuilder
{

    @SuppressWarnings( "ALL" )
    private static class WeirdMap<T> extends HashMap<T,T>{
        T getIt(T t){
            T result = get( t );
            if (result == null){
                result = t;
                put( t, t );
            }
            return result;
        }
    }

    private final WeirdMap<Dependency> deps = new WeirdMap<Dependency>(  );
    private final WeirdMap<Exclusion> exclusions = new WeirdMap<Exclusion>(  );

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


}
