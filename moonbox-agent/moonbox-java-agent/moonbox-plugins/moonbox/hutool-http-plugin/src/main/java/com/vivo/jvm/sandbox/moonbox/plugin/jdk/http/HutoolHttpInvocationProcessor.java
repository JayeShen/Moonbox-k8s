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

import cn.hutool.http.HttpResponse;
import com.alibaba.jvm.sandbox.api.event.BeforeEvent;
import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.api.event.ReturnEvent;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultInvocationProcessor;
import com.vivo.internet.moonbox.common.api.model.InvokeType;
import lombok.extern.slf4j.Slf4j;

/**
 * UniversalInvocationProcessor
 */
@Slf4j
public class HutoolHttpInvocationProcessor extends DefaultInvocationProcessor {
    public HutoolHttpInvocationProcessor(InvokeType type) {
        super(type);
    }

    public Object[] assembleRequest(BeforeEvent event){
        log.info("get hutoolhttp");
/*        if(event.argumentArray.length>0){
            for(Object o: event.argumentArray){
                log.info(o.toString());
            }
        }*/
        /*if(event.argumentArray.length > 1){
            try{
                *//*HutoolHttpMsReq resqBean = (HutoolHttpMsReq) GsonUtil.getObjectData(event.argumentArray[1].toString().replace("\\:",":"), HutoolHttpMsReq.class);
                //log.info("request service name is"+respBean.getServiceName());
                log.info(event.argumentArray[1].toString());

                Map<String, Object> params = new HashMap<>();
                params.put("serviceName", resqBean.getServiceName());
                //params.put("requestBody", resqBean.getMainSendMessage());
                params.put("reqIdentification", resqBean.getReqIdentification());
                params.put("serviceIdentification", resqBean.getServiceIdentification());
                params.put("mainSendMessage",resqBean.getMainSendMessage());
                return new Object[] { params };*//*

            }catch (Exception e){
                log.error(e.toString());
            }
        }else {
            return super.assembleRequest(event);
        }*/
        return super.assembleRequest(event);


        //return super.assembleRequest(event);
    }

    public Object assembleResponse(Event event) {
        log.info("get hutoolhttp response");

        if (event.type == Event.Type.RETURN) {
            ReturnEvent returnEvent = (ReturnEvent) event;
            // org.apache.http.HttpResponse
            Object response = returnEvent.object;
            try{
                HttpResponse httpResponse = (HttpResponse) response;
                log.info("hutool response is"+ httpResponse.body());
                return response.toString();


            }catch (Exception e) {
                log.error("hutoolhttp-plugin copy response error", e);
            }
        }
        return null;
        //return super.assembleResponse(event);
    }
}
