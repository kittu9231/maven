package org.apache.maven;

import com.google.inject.Inject;
import com.google.inject.Module;
import org.codehaus.plexus.MutablePlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.codehaus.plexus.component.composition.CycleDetectedInComponentGraphException;
import org.codehaus.plexus.component.repository.ComponentDescriptor;
import org.codehaus.plexus.component.repository.exception.ComponentLifecycleException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.codehaus.plexus.configuration.PlexusConfigurationException;
import org.codehaus.plexus.context.Context;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.LoggerManager;
import org.eclipse.sisu.plexus.PlexusBeanModule;

import java.util.List;
import java.util.Map;

/**
 * @author Kristian Rosenvold
 */
public class SynchronizedPlexusContainer implements PlexusContainer
{
    private final PlexusContainer defaultPlexusContainer;

    public SynchronizedPlexusContainer(PlexusContainer target )
        throws PlexusContainerException
    {
        this.defaultPlexusContainer = target;
    }

    public synchronized Context getContext()
    {
        return defaultPlexusContainer.getContext();
    }

    public synchronized Object lookup( String role )
        throws ComponentLookupException
    {
        return defaultPlexusContainer.lookup( role );
    }

    public synchronized Object lookup( String role, String hint )
        throws ComponentLookupException
    {
        return defaultPlexusContainer.lookup( role, hint );
    }

    public synchronized <T> T lookup( Class<T> role )
        throws ComponentLookupException
    {
        return defaultPlexusContainer.lookup( role );
    }

    public synchronized <T> T lookup( Class<T> role, String hint )
        throws ComponentLookupException
    {
        return defaultPlexusContainer.lookup( role, hint );
    }

    public synchronized <T> T lookup( Class<T> type, String role, String hint )
        throws ComponentLookupException
    {
        return defaultPlexusContainer.lookup( type, role, hint );
    }

    public synchronized List<Object> lookupList( String role )
        throws ComponentLookupException
    {
        return defaultPlexusContainer.lookupList( role );
    }

    public synchronized <T> List<T> lookupList( Class<T> role )
        throws ComponentLookupException
    {
        return defaultPlexusContainer.lookupList( role );
    }

    public synchronized Map<String, Object> lookupMap( String role )
        throws ComponentLookupException
    {
        return defaultPlexusContainer.lookupMap( role );
    }

    public synchronized <T> Map<String, T> lookupMap( Class<T> role )
        throws ComponentLookupException
    {
        return defaultPlexusContainer.lookupMap( role );
    }

    public synchronized boolean hasComponent( String role )
    {
        return defaultPlexusContainer.hasComponent( role );
    }

    public synchronized boolean hasComponent( String role, String hint )
    {
        return defaultPlexusContainer.hasComponent( role, hint );
    }

    public synchronized void addComponent( Object component, String role )
    {
        defaultPlexusContainer.addComponent( component, role );
    }

    @SuppressWarnings( "deprecation" )
    public synchronized <T> void addComponent( T component, Class<?> role, String hint )
    {
        defaultPlexusContainer.addComponent( component, role, hint );
    }

    public synchronized <T> void addComponentDescriptor( ComponentDescriptor<T> descriptor )
        throws CycleDetectedInComponentGraphException
    {
        defaultPlexusContainer.addComponentDescriptor( descriptor );
    }

    public synchronized ComponentDescriptor<?> getComponentDescriptor( String role, String hint )
    {
        return defaultPlexusContainer.getComponentDescriptor( role, hint );
    }

    public synchronized <T> ComponentDescriptor<T> getComponentDescriptor( Class<T> type, String role, String hint )
    {
        return defaultPlexusContainer.getComponentDescriptor( type, role, hint );
    }

    public synchronized List getComponentDescriptorList( String role )
    {
        return defaultPlexusContainer.getComponentDescriptorList( role );
    }

    public synchronized <T> List<ComponentDescriptor<T>> getComponentDescriptorList( Class<T> type, String role )
    {
        return defaultPlexusContainer.getComponentDescriptorList( type, role );
    }

    public synchronized Map getComponentDescriptorMap( String role )
    {
        return defaultPlexusContainer.getComponentDescriptorMap( role );
    }

    public synchronized <T> Map<String, ComponentDescriptor<T>> getComponentDescriptorMap( Class<T> type, String role )
    {
        return defaultPlexusContainer.getComponentDescriptorMap( type, role );
    }

    public synchronized List<ComponentDescriptor<?>> discoverComponents( ClassRealm realm )
        throws PlexusConfigurationException
    {
        return defaultPlexusContainer.discoverComponents( realm );
    }


    public synchronized ClassRealm getContainerRealm()
    {
        return defaultPlexusContainer.getContainerRealm();
    }

    public synchronized ClassRealm setLookupRealm( ClassRealm realm )
    {
        return defaultPlexusContainer.setLookupRealm( realm );
    }

    public synchronized ClassRealm getLookupRealm()
    {
        return defaultPlexusContainer.getLookupRealm();
    }

    public synchronized ClassRealm createChildRealm( String id )
    {
        return defaultPlexusContainer.createChildRealm( id );
    }

    public synchronized void release( Object component )
        throws ComponentLifecycleException
    {
        defaultPlexusContainer.release( component );
    }

    public synchronized void releaseAll( Map<String, ?> components )
        throws ComponentLifecycleException
    {
        defaultPlexusContainer.releaseAll( components );
    }

    public synchronized void releaseAll( List<?> components )
        throws ComponentLifecycleException
    {
        defaultPlexusContainer.releaseAll( components );
    }

    public synchronized void dispose()
    {
        defaultPlexusContainer.dispose();
    }

    public synchronized boolean hasComponent( Class<?> role )
    {
        return defaultPlexusContainer.hasComponent( role );
    }

    public synchronized boolean hasComponent( Class<?> role, String hint )
    {
        return defaultPlexusContainer.hasComponent( role, hint );
    }

    public boolean hasComponent( Class<?> type, String role, String hint )
    {
        return defaultPlexusContainer.hasComponent( type, role, hint );
    }
}
