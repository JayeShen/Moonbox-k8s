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
package com.vivo.internet.moonbox.service.data.model.replay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ReplayUriCountQuery - {@link ReplayUriCountQuery}
 *
 * @author yanjiang.liu
 * @version 1.0
 * @since 2022/8/22 14:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplayUriCountQuery implements Serializable {
    private static final long serialVersionUID = 7270784257006175792L;

    /**
     * 回放任务id
     */
    private String  replayTaskRunId;

    /**
     * 回放接口模糊匹配
     */
    private String  uriMatchCondition;

    /**
     * 回放状态{@link com.vivo.internet.moonbox.common.api.constants.ReplayStatus}
     */
    private Integer replayStatus;
}
