package org.apache.maven.model.immutable.model;

import org.apache.maven.model.immutable.ModelElement;

/**
 * Created by kristian on 20.09.15.
 */
public class Plugins
    extends ModelElement
{
    private final Plugin[] plugins;


    public Plugins( Plugin[] plugins )
    {
        this.plugins = plugins;
    }
}
