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
package com.vivo.internet.moonbox.web.console;

import com.vivo.internet.moonbox.common.api.dto.MoonBoxResult;
import com.vivo.internet.moonbox.service.console.AppService;
import com.vivo.internet.moonbox.service.console.util.UserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RequestMapping("/api")
@RestController
public class TestController {

    /**
     * 获取应用列表
     *
     * @return 应用列表
     */
    @PostMapping(value = "test")
    @ResponseBody
    public MoonBoxResult<List<String>> test(String name) {
        System.out.println("name = " + name);
        return MoonBoxResult.createSuccess();
    }

}
