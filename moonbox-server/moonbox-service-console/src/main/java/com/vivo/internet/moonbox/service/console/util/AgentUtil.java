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
package com.vivo.internet.moonbox.service.console.util;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.vivo.internet.moonbox.service.common.ex.BusiException;
import com.vivo.internet.moonbox.service.common.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * AgentUtil - {@link AgentUtil}
 *
 * @author yanjiang.liu
 * @version 1.0
 * @since 2022/10/17 14:59
 */
@Slf4j
public class AgentUtil {

    private static String SANDBOX_JVM_OPS = "-Xms128M -Xmx128M -Xnoclassgc -ea -Xbootclasspath/a:%slib" + File.separator + "tools.jar";

    public static String SANDBOX_HOME = System.getProperty("user.home") + File.separator + "sandbox";

    public static String SANDBOX_MODULE = System.getProperty("user.home") + File.separator + ".sandbox-module";

    private static String SANDBOX_CORE_JAR_PATH = SANDBOX_HOME + File.separator + "lib" + File.separator + "sandbox-core.jar ";

    private static String SANDBOX_AGENT_JAR_PATH = SANDBOX_HOME + File.separator + "lib"+File.separator+"sandbox-agent.jar";

    private static String SANDBOX_TARGET_SERVER_IP = "0.0.0.0";

    private static String SANDBOX_TARGET_SERVER_PORT = "8820";

    private static String DEFAULT_NAME_SPACE = "default";

    private static String SANDBOX_PARAM = "home=%s;token=%s;server.ip=%s;server.port=%s;namespace=%s;task.config=%s";

    /**
     * 判断是否为windows系统
     *
     * @return 判断结果
     */
    public static boolean isWindowsOrMacOs() {
        String os = System.getProperty("os.name").toLowerCase();
//        return os.contains("windows") || os.contains("mac");
        return os.contains("windows");
    }

    /**
     * 获取start配置
     *
     * @param serverUrl        agent 数据上报url地址
     * @param taskRunId        运行任务id
     * @param sandboxLogLevel  sandbox日志级别
     * @param repeaterLogLevel repeater agent日志级别
     * @return agent启动参数
     * @throws Exception 异常信息
     */
    public static String getAgentStartConfig(String serverUrl, String taskRunId, String sandboxLogLevel, String repeaterLogLevel) throws Exception {
        String[] taskRunConfigArray = new String[4];
        taskRunConfigArray[0] = taskRunId;
        taskRunConfigArray[1] = serverUrl;
        taskRunConfigArray[2] = sandboxLogLevel;
        taskRunConfigArray[3] = repeaterLogLevel;
        return URLEncoder.encode(Joiner.on('&').join(taskRunConfigArray), Charsets.UTF_8.name());
    }

    public static String getLocalAgentStartCommand(String appName, String taskConfig) throws Exception {
        String pid = "";
        String jpsResult = javaCommandExecute("jps -v");
        for (String command : Splitter.on('\n').omitEmptyStrings().trimResults().splitToList(jpsResult)) {
            if (command.contains(appName)) {
                pid = Splitter.on(' ').trimResults().splitToList(command).get(0);
                break;
            }
        }
        if (StringUtils.isBlank(pid)) {
            BusiException.throwsEx(appName + " 没有找到应用进程，请确保应用启动或者jvm启动参数添加-Dapp.name=" + appName);
        }
        String javaHome = System.getProperty("java.home");
        if (javaHome.endsWith("jre")) {
            javaHome = javaHome.substring(0, javaHome.length() - "jre".length());
        }
        String agentParam = String.format(SANDBOX_PARAM, SANDBOX_HOME, IdGenerator.uuid(), SANDBOX_TARGET_SERVER_IP, SANDBOX_TARGET_SERVER_PORT, DEFAULT_NAME_SPACE, taskConfig);
        String sandboxJvmOps = String.format(SANDBOX_JVM_OPS, javaHome);
        return "java -jar " + sandboxJvmOps + " "
                +SANDBOX_CORE_JAR_PATH+" "+ pid  +" "+SANDBOX_AGENT_JAR_PATH+" "+agentParam;
    }

    /**
     * 获取远程服务器启动agent命令
     *
     * @param appName    应用名称
     * @param taskConfig 任务配置
     * @return 启动shell 脚本
     * @throws Exception 抛出异常
     */
    public static String getRemoteAgentStartCommand(String sandboxDownLoadUrl, String moonboxDownLoadUrl, String appName, String taskConfig) {
        //从服务端下载moonbox 和 sandbox 两个agent并解压。然后格式化启动脚本
        String downLoadCommand = "curl -o sandboxDownLoad.tar " + sandboxDownLoadUrl + " && curl -o moonboxDownLoad.tar " + moonboxDownLoadUrl;

        String rmDir = " && rm -fr ~/sandbox && rm -fr ~/.sandbox-module";

        String unzipCommand=" &&  tar  -xzf sandboxDownLoad.tar -C ~/ >> /dev/null && tar  -xzf moonboxDownLoad.tar -C ~/ >> /dev/null";

        //格式化脚本
        String dos2unixSh = " && dos2unix ~/sandbox/bin/sandbox.sh && dos2unix ~/.sandbox-module/bin/start-remote-agent.sh";

        //删除文件
        String rmAgentZip = " && rm -f moonboxDownLoad.tar sandboxDownLoad.tar";

        //start-remote-agent脚本来拉起来agent
        String startAgentCommand = " && sh ~/.sandbox-module/bin/start-remote-agent.sh " + appName +" "+ taskConfig;
//        return downLoadCommand + rmDir + unzipCommand + dos2unixSh + rmAgentZip + startAgentCommand;
//        return " bash /weblogic/.sandbox-module/bin/start-remote-agent.sh " + appName +" "+ taskConfig;
        return appName +" "+ taskConfig;
    }



    /**
     * 本地执行windows命令
     * @param exeCommand 命令
     * @return 执行结果
     * @throws Exception 异常
     */
    public static String javaCommandExecute(String exeCommand) throws Exception {
        StringBuilder errorBuilder = new StringBuilder();
        StringBuilder inputBuilder = new StringBuilder();
        Process process = Runtime.getRuntime().exec(exeCommand);
        new InputStreamRunnable(process.getInputStream(), "input", inputBuilder).start();
        new InputStreamRunnable(process.getErrorStream(), "error ", errorBuilder).start();
        process.waitFor();
        return inputBuilder.append(errorBuilder.toString()).toString();
    }

    static class InputStreamRunnable extends Thread {
        BufferedReader bReader = null;
        String type = null;
        StringBuilder builder = null;
        public InputStreamRunnable(InputStream is, String _type,StringBuilder builder) {
            try {
                bReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is), "UTF-8"));
                type = _type;
                this.builder = builder;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        @Override
        public void run() {
            String line;
            try {
                while ((line = bReader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                bReader.close();
            } catch (Exception ex) {
                builder.append(ex.getMessage());
            }
        }
    }
}
