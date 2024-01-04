package com.vivo.jvm.sandbox.moonbox.proxy.util;

import org.apache.logging.log4j.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteShellCommandUtil {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ExecuteShellCommandUtil.class);

    public static String executeCommand(String command) {
        try {
            System.out.println("开始执行命令："+command);
            ProcessBuilder processBuilder = new ProcessBuilder();
            // 设置环境变量
//            Map<String, String> environment = processBuilder.environment();
//            environment.put("PATH", "/usr/local/bin:/usr/bin:/bin");
            //将错误内容也输出
            processBuilder.redirectErrorStream(true);
            // 在这里设置Shell命令，例如Linux下的ls命令
            processBuilder.command("/bin/sh", "-c", command);
            //pod内不支持bash，请使用sh
//            processBuilder.command("sh", "-c", command);

            // 设置工作目录（如果需要）
            // processBuilder.directory(new File("your_directory_path"));

            Process process = processBuilder.start();

            // 读取命令的输出
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                logger.info("命令执行失败，错误代码: " + exitCode);
            }

            return output.toString();
        } catch (IOException | InterruptedException e) {
            logger.error("命令执行异常 " + e);
            return null;
        }

    }
}
