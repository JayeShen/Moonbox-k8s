/*
Copyright 2022 vivo Communication Technology Co., Ltd.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.vivo.jvm.sandbox.moonbox.plugin.jdk.http;

import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.repeater.plugin.api.InvocationProcessor;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.spi.AbstractInvokePluginAdapter;
import com.alibaba.jvm.sandbox.repeater.plugin.core.model.EnhanceModel;
import com.alibaba.jvm.sandbox.repeater.plugin.spi.InvokePlugin;
import com.google.common.collect.Lists;
import com.vivo.internet.moonbox.common.api.model.InvokeType;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.MetaInfServices;

import java.util.List;

/**
 * jdkhttpplugin
 */
@Slf4j
@MetaInfServices(InvokePlugin.class)
public class HutoolHttpPlugin extends AbstractInvokePluginAdapter {

    @Override
    protected List<EnhanceModel> getEnhanceModels() {
        //TODO
/*        EnhanceModel enhanceModel = EnhanceModel.builder().classPattern("com.cpic.preservation.customer.utils.HttpCommonUtil")
                .includeSubClasses(true)
                .methodPatterns(EnhanceModel.MethodPattern.transform(
                        "execute"
                ))
                .watchTypes(Event.Type.BEFORE, Event.Type.RETURN, Event.Type.THROWS)
                .build();*/


        EnhanceModel enhanceModel = EnhanceModel.builder().classPattern("cn.hutool.http.HttpRequest")
                .includeSubClasses(true)
                .methodPatterns(EnhanceModel.MethodPattern.transform(
                        "execute"
                ))
                .watchTypes(Event.Type.BEFORE, Event.Type.RETURN, Event.Type.THROWS)
                .build();




        return Lists.newArrayList(enhanceModel);
    }

    @Override
    protected InvocationProcessor getInvocationProcessor() {
        return new HutoolHttpInvocationProcessor(getType());
    }

    @Override
    public InvokeType getType() {
        return InvokeType.HUTOOL_HTTP;
    }

    @Override
    public String identity() {
        return InvokeType.HUTOOL_HTTP.getInvokeName();
    }

    @Override
    public boolean isEntrance() {
        return false;
    }
}
