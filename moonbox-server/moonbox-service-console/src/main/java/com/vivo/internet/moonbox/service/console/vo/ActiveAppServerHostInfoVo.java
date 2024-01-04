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
package com.vivo.internet.moonbox.service.console.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ActiveHostInfoVo - {@link ActiveAppServerHostInfoVo}
 *
 * @author JayeShen
 * @version 1.0
 * @since 2023/9/28 16:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveAppServerHostInfoVo {
    private Long id;
    private String systemCode;
    private String systemName;
    private String appName;
    private String channelId;
    private Date lastHeartbeatTime;
    private String ip;
    private String remark;
    private String creator;
    private String appEnv;
    private Date createTime;
    private Date updateTime;
    private Integer onlineState;
}
