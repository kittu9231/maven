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

import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings( { "MethodWithTooManyParameters", "MethodMayBeStatic" } )
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
    private final WeirdMap<RepositoryBase> repositoryBase = new WeirdMap<RepositoryBase>(  );
    private final WeirdMap<RepositoryPolicy> repositoryPolicy = new WeirdMap<RepositoryPolicy>(  );
    private final WeirdMap<Repository> repository = new WeirdMap<Repository>(  );
    private final WeirdMap<Prerequisites> prerequisites = new WeirdMap<Prerequisites>(  );
    private final HashArray<Developer> developers = new HashArray<Developer>();
    private final HashArray<Contributor> contributors = new HashArray<Contributor>();
    private final WeirdMap<MailingList> mailingList = new WeirdMap<MailingList>();
    private final WeirdMap<License> license = new WeirdMap<License>(  );
    private final WeirdMap<FileSet> fileSet = new WeirdMap<FileSet>(  );
    private final WeirdMap<Parent> parent = new WeirdMap<Parent>(  );





    public RepositoryBase createRepositoryBase( String id, String name, String url, String layout,
                                                       Map<Object, InputLocation> locations )
    {
        return repositoryBase.getIt( new RepositoryBase( RepositoryBase.hashCode( id, name, url, layout ), id, name, url, layout, locations ));
    }

    public Repository createRepository( String id, String name, String url, String layout,
                                               Map<Object, InputLocation> locations, RepositoryPolicy releases,
                                               RepositoryPolicy snapshots )
    {
        return repository.getIt( new Repository( id, name, url, layout, locations, releases, snapshots ));
    }

    public RepositoryPolicy createRepositoryPolicy( String enabled, String updatePolicy, String checksumPolicy,
                                                           Map<Object, InputLocation> locations )
    {
        return repositoryPolicy.getIt( new RepositoryPolicy( enabled, updatePolicy, checksumPolicy, locations ));
    }

    public Prerequisites createPrerequisites( String maven, Map<Object, InputLocation> locations )
    {
        return prerequisites.getIt( new Prerequisites( maven, locations ));
    }

    public ReportSet createReportSet( String inherited, Object configuration,
                                             Map<Object, InputLocation> locations, String id, List<String> reports )
    {
        return new ReportSet( inherited, configuration, locations, id, reports );
    }

    public Reporting createReporting( String excludeDefaults, String outputDirectory, List<ReportPlugin> plugins,
                                             Map<Object, InputLocation> locations )
    {
        return new Reporting( excludeDefaults, outputDirectory, plugins, locations );
    }

    public Relocation createRelocation( String groupId, String artifactId, String version, String message,
                                               Map<Object, InputLocation> locations )
    {
        return new Relocation( groupId, artifactId, version, message, locations );
    }

    public Profile createProfile( List<String> modules, DistributionManagement distributionManagement,
                                         Properties properties, DependencyManagement dependencyManagement,
                                         List<Dependency> dependencies, List<Repository> repositories,
                                         List<Repository> pluginRepositories, Xpp3Dom reports, Reporting reporting,
                                         Map<Object, InputLocation> locations, String id, Activation activation,
                                         BuildBase build )
    {
        return new Profile( modules, distributionManagement, properties, dependencyManagement, dependencies,
                            repositories, pluginRepositories, reports, reporting, locations, id, activation, build );
    }

    public PluginManagement createPluginManagement( List<Plugin> plugins, Map<Object, InputLocation> locations )
    {
        return new PluginManagement( plugins, locations );
    }

    public PluginExecution createPluginExecution( String inherited, Object configuration,
                                                         Map<Object, InputLocation> locations, String id, String phase,
                                                         List<String> goals )
    {
        return new PluginExecution( inherited, configuration, locations, id, phase, goals );
    }

    public PluginContainer createPluginContainer( List<Plugin> plugins, Map<Object, InputLocation> locations )
    {
        return new PluginContainer( plugins, locations );
    }

    public PluginConfiguration createPluginConfiguration( List<Plugin> plugins,
                                                                 Map<Object, InputLocation> locations,
                                                                 PluginManagement pluginManagement )
    {
        return new PluginConfiguration( plugins, locations, pluginManagement );
    }


    public Parent createParent( String groupId, String artifactId, String version, String relativePath,
                                       Map<Object, InputLocation> locations )
    {
        int hc = Parent.hashCode( groupId, artifactId, version, relativePath );
        return parent.getIt( new Parent( hc, groupId, artifactId, version, relativePath, locations ));
    }

    public Organization createOrganization( String name, String url, Map<Object, InputLocation> locations )
    {
        return new Organization( name, url, locations );
    }

    public Notifier createNotifier( String type, Boolean sendOnError, Boolean sendOnFailure,
                                           Boolean sendOnSuccess, Boolean sendOnWarning, String address,
                                           Properties configuration, Map<Object, InputLocation> locations )
    {
        return new Notifier( type, sendOnError, sendOnFailure, sendOnSuccess, sendOnWarning, address, configuration,
                             locations );
    }

    public ModelBase createModelBase( List<String> modules, DistributionManagement distributionManagement,
                                             Properties properties, DependencyManagement dependencyManagement,
                                             List<Dependency> dependencies, List<Repository> repositories,
                                             List<Repository> pluginRepositories, Object reports, Reporting reporting,
                                             Map<Object, InputLocation> locations )
    {
        return new ModelBase( modules, distributionManagement, properties, dependencyManagement, dependencies,
                              repositories, pluginRepositories, reports, reporting, locations );
    }

    public Model createModel( List<String> modules, DistributionManagement distributionManagement,
                                     Properties properties, DependencyManagement dependencyManagement,
                                     List<Dependency> dependencies, List<Repository> repositories,
                                     List<Repository> pluginRepositories, Object reports, Reporting reporting,
                                     Map<Object, InputLocation> locations, String modelVersion, Parent parent,
                                     String groupId, String artifactId, String version, String packaging, String name,
                                     String description, String url, String inceptionYear, Organization organization,
                                     List<License> licenses, List<Developer> developers, List<Contributor> contributors,
                                     List<MailingList> mailingLists, Prerequisites prerequisites, Scm scm,
                                     IssueManagement issueManagement, CiManagement ciManagement, Build build,
                                     List<Profile> profiles, String modelEncoding )
    {
        return new Model( modules, distributionManagement, properties, dependencyManagement, dependencies, repositories,
                          pluginRepositories, reports, reporting, locations, modelVersion, parent, groupId, artifactId,
                          version, packaging, name, description, url, inceptionYear, organization, licenses, developers,
                          contributors, mailingLists, prerequisites, scm, issueManagement, ciManagement, build,
                          profiles, modelEncoding );
    }

    public MailingList createMailingList( String name, String subscribe, String unsubscribe, String post,
                                                 String archive, List<String> otherArchives,
                                                 Map<Object, InputLocation> locations )
    {
        return mailingList.getIt( new MailingList( name, subscribe, unsubscribe, post, archive, otherArchives, locations ));
    }

    public License createLicense( String name, String url, String distribution, String comments,
                                         Map<Object, InputLocation> locations )
    {
        return license.getIt( new License( name, url, distribution, comments, locations ));
    }

    public IssueManagement createIssueManagement( String system, String url,
                                                         Map<Object, InputLocation> locations )
    {
        return new IssueManagement( system, url, locations );
    }

    public static InputSource createInputSource( String modelId, String location )
    {
        return new InputSource( modelId, location );
    }

    public FileSet createFileSet( List<String> includes, List<String> excludes,
                                         Map<Object, InputLocation> locations, String directory )
    {
        return fileSet.getIt( new FileSet( includes, excludes, locations, directory ));
    }

    public Extension createExtension( String groupId, String artifactId, String version,
                                             Map<Object, InputLocation> locations )
    {
        return new Extension( groupId, artifactId, version, locations );
    }

    public DistributionManagement createDistributionManagement( DeploymentRepository repository,
                                                                       DeploymentRepository snapshotRepository,
                                                                       Site site, String downloadUrl,
                                                                       Relocation relocation, String status,
                                                                       Map<Object, InputLocation> locations )
    {
        return new DistributionManagement( repository, snapshotRepository, site, downloadUrl, relocation, status,
                                           locations );
    }


    public DeploymentRepository createDeploymentRepository( String id, String name, String url, String layout,
                                                                   Map<Object, InputLocation> locations,
                                                                   RepositoryPolicy releases,
                                                                   RepositoryPolicy snapshots, Boolean uniqueVersion )
    {
        return new DeploymentRepository( id, name, url, layout, locations, releases, snapshots, uniqueVersion );
    }

    public DependencyManagement createDependencyManagement( List<Dependency> dependencies,
                                                                   Map<Object, InputLocation> locations )
    {
        return new DependencyManagement( dependencies, locations );
    }

    public Contributor createContributor( int hashCode, String name, String email, String url,
                                                 String organization, String organizationUrl, List<String> roles,
                                                 String timezone, Properties properties,
                                                 Map<Object, InputLocation> locations )
    {
        return new Contributor( hashCode, name, email, url, organization, organizationUrl, roles, timezone, properties,
                                locations );
    }

    public ConfigurationContainer createConfigurationContainer( String inherited, Object configuration,
                                                                       Map<Object, InputLocation> locations )
    {
        return new ConfigurationContainer( inherited, configuration, locations );
    }

    public  CiManagement createCiManagement( String system, String url, List<Notifier> notifiers,
                                                   Map<Object, InputLocation> locations )
    {
        return new CiManagement( system, url, notifiers, locations );
    }

    public BuildBase createBuildBase( List<Plugin> plugins, Map<Object, InputLocation> locations,
                                             PluginManagement pluginManagement, String defaultGoal,
                                             List<Resource> resources, List<Resource> testResources, String directory,
                                             String finalName, List<String> filters )
    {
        return new BuildBase( plugins, locations, pluginManagement, defaultGoal, resources, testResources, directory,
                              finalName, filters );
    }

    public  Build createBuild( List<Plugin> plugins, Map<Object, InputLocation> locations,
                                     PluginManagement pluginManagement, String defaultGoal, List<Resource> resources,
                                     List<Resource> testResources, String directory, String finalName,
                                     List<String> filters, String sourceDirectory, String scriptSourceDirectory,
                                     String testSourceDirectory, String outputDirectory, String testOutputDirectory,
                                     List<Extension> extensions )
    {
        return new Build( plugins, locations, pluginManagement, defaultGoal, resources, testResources, directory,
                          finalName, filters, sourceDirectory, scriptSourceDirectory, testSourceDirectory,
                          outputDirectory, testOutputDirectory, extensions );
    }

    public ActivationProperty createActivationProperty( String name, String value,
                                                               Map<Object, InputLocation> locations )
    {
        return new ActivationProperty( name, value, locations );
    }

    public ActivationOS createActivationOS( String name, String family, String arch, String version,
                                                   Map<Object, InputLocation> locations )
    {
        return new ActivationOS( name, family, arch, version, locations );
    }

    public ActivationFile createActivationFile( String missing, String exists,
                                                       Map<Object, InputLocation> locations )
    {
        return new ActivationFile( missing, exists, locations );
    }

    public Activation createActivation( Boolean activeByDefault, String jdk, ActivationOS os,
                                               ActivationProperty property, ActivationFile file,
                                               Map<Object, InputLocation> locations )
    {
        return new Activation( activeByDefault, jdk, os, property, file, locations );
    }

    public Resource createResource( List<String> includes, List<String> excludes,
                                           Map<Object, InputLocation> locations, String directory, String targetPath,
                                           String filtering )
    {
        return new Resource( includes, excludes, locations, directory, targetPath, filtering );
    }




    public Developer createDeveloper( String name, String email, String url, String organization,
                                             String organizationUrl, List<String> roles, String timezone,
                                             Properties properties, Map<Object, InputLocation> locations, String id )
    {
        int hc =
            Developer.hashCode( id, name, email, url, organization, organizationUrl, roles, timezone, properties );
        ArrayList<Developer> it = developers.getIt( hc );
        Developer c =
            createDeveloper( hc, name, email, url, organization, organizationUrl, roles, timezone, properties,
                             locations, id );

        for ( Developer contributor : it )
        {
            if (contributor.equals( c )) return contributor;
        }

        it.add( c);
        return c;
    }

    private Developer createDeveloper( int hashCode, String name, String email, String url, String organization,
                                      String organizationUrl, List<String> roles, String timezone,
                                      Properties properties, Map<Object, InputLocation> locations, String id )
    {
        return new Developer( hashCode, name, email, url, organization, organizationUrl, roles, timezone, properties,
                              locations, id );
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
            createContributor( hc, name, email, url, organization, organizationUrl, roles, timezone, properties,
                               locations );
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

    public static Exclusion doCreateExclusion( String artifactId, String groupId, Map<Object, InputLocation> locations )
    {
        int hc = Exclusion.hashCode( artifactId, groupId );
        return new Exclusion( hc, artifactId, groupId, locations );
    }

    public Exclusion createExclusion( String artifactId, String groupId, Map<Object, InputLocation> locations )
    {
        return exclusions.getIt( doCreateExclusion( artifactId, groupId, locations ) );
    }


    static Plugin doCreatePlugin( String inherited, Object configuration, Map<Object, InputLocation> locations,
                                String groupId, String artifactId, String version, String extensions,
                                List<PluginExecution> executions, List<Dependency> dependencies, Xpp3Dom goals )
    {
        return new Plugin( inherited, configuration, locations, groupId, artifactId, version, extensions, executions,
                           dependencies, goals );
    }

    public Plugin createPlugin( String inherited, Object configuration, Map<Object, InputLocation> locations,
                                       String groupId, String artifactId, String version, String extensions,
                                       List<PluginExecution> executions, List<Dependency> dependencies, Xpp3Dom goals )
    {
        Plugin plugin1 =
            doCreatePlugin( inherited, configuration, locations, groupId, artifactId, version, extensions, executions,
                            dependencies, goals );
        return plugin.getIt( plugin1 );
    }

    private static PatternSet doCreatePatternSet( List<String> includes, List<String> excludes,
                                               Map<Object, InputLocation> locations )
    {
        return new PatternSet( PatternSet.hashCode( includes, excludes ), includes, excludes, locations );
    }

    public PatternSet createPatternSet( List<String> includes, List<String> excludes, Map<Object, InputLocation> locations )
    {
        PatternSet patternSet = doCreatePatternSet( includes, excludes, locations );
        return patternSets.getIt( patternSet );
    }

    public Site createSite( String id, String name, String url, Map<Object, InputLocation> locations )
    {
        return new Site( id, name, url, locations );
    }

    public Scm createScm( String connection, String developerConnection, String tag, String url,
                                 Map<Object, InputLocation> locations )
    {
        return new Scm( connection, developerConnection, tag, url, locations );
    }

}
