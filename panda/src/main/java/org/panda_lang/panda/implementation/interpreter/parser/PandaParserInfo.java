/*
 * Copyright (c) 2015-2016 Dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.panda_lang.panda.implementation.interpreter.parser;

import org.panda_lang.framework.interpreter.parser.ParserInfo;

import java.util.HashMap;
import java.util.Map;

public class PandaParserInfo implements ParserInfo {

    private final Map<String, Object> components;

    public PandaParserInfo() {
        this(new HashMap<>());
    }

    private PandaParserInfo(Map<String, Object> components) {
        this.components = components;
    }

    @Override
    public PandaParserInfo clone() {
        return new PandaParserInfo(new HashMap<>(components));
    }

    @Override
    public void setComponent(String componentName, Object component) {
        this.components.put(componentName, component);
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public <T> T getComponent(String componentName) {
        return (T) components.get(componentName);
    }

}
