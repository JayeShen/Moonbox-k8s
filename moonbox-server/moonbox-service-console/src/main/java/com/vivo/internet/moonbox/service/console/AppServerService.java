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
package com.vivo.internet.moonbox.service.console;

import com.vivo.internet.moonbox.service.console.vo.ActiveAppServerHostInfoVo;

import java.util.List;

/**
 * - {@link AppServerService}
 *
 * @author JayeShen
 * @version 1.0
 * @since 2023/9/28 10:42
 */
public interface AppServerService {

    /**
     * get log list
     * @param appName query request
     * @return page query result
     */
    List<ActiveAppServerHostInfoVo> selectAppServerHostInfoListFromAppName(String appName);

    List<ActiveAppServerHostInfoVo> selectAppServerHostInfoList(ActiveAppServerHostInfoVo activeAppServerHostInfoVo);

    List<ActiveAppServerHostInfoVo> selectAppServerHostInfoListFromOnlineState(ActiveAppServerHostInfoVo activeAppServerHostInfoVo);

    List<ActiveAppServerHostInfoVo> selectAppServerHostInfoListFromChannelId(ActiveAppServerHostInfoVo activeAppServerHostInfoVo);

    /**
     * add server ip
     * @param activeAppServerHostInfoVo add request
     */
    void insertAppServerHostInfo(ActiveAppServerHostInfoVo activeAppServerHostInfoVo);

    /**
     * update ip
     * @param activeAppServerHostInfoVo add request
     */
    void updateAppServerHostInfo(ActiveAppServerHostInfoVo activeAppServerHostInfoVo);
}
