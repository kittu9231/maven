package org.apache.maven.properties.internal;

import java.util.Properties;

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

/**
 * @since 3.2.3
 */
public class SystemProperties
{
    /**
     * Thread-safe System.properties copy implementation.
     * 
     * @see http://jira.codehaus.org/browse/MNG-5670
     */
	public static Properties addSystemProperties( Properties target )
    {
		return threadSafeCopyProperties( target, System.getProperties() );
    }

    /**
     * Copes the source collection safely, guarding against concurrent modification
     * on both source and target
     */
    @SuppressWarnings( "SynchronizationOnLocalVariableOrMethodParameter" )
    public static Properties threadSafeCopyProperties( Properties target, Properties source )
    {
        synchronized ( source )
        {
            target.putAll( source );
        }
        return target;
    }

    /**
     * Returns System.properties copy.
     */
    public static Properties getSystemProperties()
    {
        return addSystemProperties( new Properties() );
    }
}
