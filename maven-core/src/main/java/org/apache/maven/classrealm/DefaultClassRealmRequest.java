package org.apache.maven.classrealm;

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

import java.util.List;

/**
 * @author Benjamin Bentmann
 */
class DefaultClassRealmRequest
    implements ClassRealmRequest
{

    private final RealmType type;

    private final ClassLoader parent;

    private final List<String> imports;

    private final List<ClassRealmConstituent> constituents;

    public DefaultClassRealmRequest( RealmType type, ClassLoader parent, List<String> imports,
                                     List<ClassRealmConstituent> constituents )
    {
        this.type = type;
        this.parent = parent;
        this.imports = imports;
        this.constituents = constituents;
    }

    public RealmType getType()
    {
        return type;
    }

    public ClassLoader getParent()
    {
        return parent;
    }

    public List<String> getImports()
    {
        return imports;
    }

    public List<ClassRealmConstituent> getConstituents()
    {
        return constituents;
    }

}
