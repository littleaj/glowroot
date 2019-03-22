/*
 * Copyright 2015-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.glowroot.agent.init;

import java.io.IOException;
import java.util.List;

import org.glowroot.agent.collector.Collector.AgentConfigUpdater;
import org.glowroot.agent.config.AllConfig;
import org.glowroot.agent.config.ConfigService;
import org.glowroot.engine.config.InstrumentationDescriptor;
import org.glowroot.wire.api.model.AgentConfigOuterClass.AgentConfig;

class ConfigUpdateService implements AgentConfigUpdater {

    private final ConfigService configService;
    private final List<InstrumentationDescriptor> instrumentationDescriptors;

    private final Object lock = new Object();

    ConfigUpdateService(ConfigService configService,
            List<InstrumentationDescriptor> instrumentationDescriptors) {
        this.configService = configService;
        this.instrumentationDescriptors = instrumentationDescriptors;
    }

    // ui config, synthetic monitor configs and alert configs are not used by agent, but updated
    // here to keep config.json in sync with central, to allow copying to another deployment
    @Override
    public void update(AgentConfig agentConfig) throws IOException {
        synchronized (lock) {
            configService
                    .updateAllConfig(AllConfig.create(agentConfig, instrumentationDescriptors));
        }
    }
}
