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

import java.util.List;
import java.util.Map;

public class MailingList extends FixedHashCode
{

    private String name;
    private String subscribe;
    private String unsubscribe;
    private String post;
    private String archive;
    private java.util.List<String> otherArchives;
    private java.util.Map<Object, InputLocation> locations;

    MailingList( String name, String subscribe, String unsubscribe, String post, String archive,
                         List<String> otherArchives, Map<Object, InputLocation> locations )
    {
        super(hashCode( name, subscribe, unsubscribe, post, archive, otherArchives ));
        this.name = name;
        this.subscribe = subscribe;
        this.unsubscribe = unsubscribe;
        this.post = post;
        this.archive = archive;
        this.otherArchives = otherArchives;
        this.locations = locations;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        MailingList that = (MailingList) o;

        if ( archive != null ? !archive.equals( that.archive ) : that.archive != null )
        {
            return false;
        }
        if ( name != null ? !name.equals( that.name ) : that.name != null )
        {
            return false;
        }
        if ( otherArchives != null ? !otherArchives.equals( that.otherArchives ) : that.otherArchives != null )
        {
            return false;
        }
        if ( post != null ? !post.equals( that.post ) : that.post != null )
        {
            return false;
        }
        if ( subscribe != null ? !subscribe.equals( that.subscribe ) : that.subscribe != null )
        {
            return false;
        }
        if ( unsubscribe != null ? !unsubscribe.equals( that.unsubscribe ) : that.unsubscribe != null )
        {
            return false;
        }

        return true;
    }

    public static int hashCode( String name1, String subscribe1, String unsubscribe1, String post1, String archive1,
                         List<String> otherArchives1 )
    {
        int result = name1 != null ? name1.hashCode() : 0;
        result = 31 * result + ( subscribe1 != null ? subscribe1.hashCode() : 0 );
        result = 31 * result + ( unsubscribe1 != null ? unsubscribe1.hashCode() : 0 );
        result = 31 * result + ( post1 != null ? post1.hashCode() : 0 );
        result = 31 * result + ( archive1 != null ? archive1.hashCode() : 0 );
        result = 31 * result + ( otherArchives1 != null ? otherArchives1.hashCode() : 0 );
        return result;
    }
}
