package com.vivo.jvm.sandbox.moonbox.proxy;

import com.vivo.jvm.sandbox.moonbox.proxy.util.BootNettyClient;
import com.vivo.jvm.sandbox.moonbox.proxy.util.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.vivo.jvm.sandbox.moonbox.proxy.util.BootNettyChannelInboundHandlerAdapter.reconnect;

public class MoonboxAgentProxy {

    private static final Logger logger = LogManager.getLogger(MoonboxAgentProxy.class);
    /**
     * 注意方法名必须是premain
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        logger.info("----------moonbox-agent 开始部署-----------");
        Thread tcpTask = new Thread(new TcpTask());
        tcpTask.start();
        logger.info("----------moonbox-agent 部署完成-----------");
        String appName = System.getProperty("appName");
        logger.info("----------appName======"+appName+"----------");
    }

 private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private static class TcpTask implements Runnable {
        @Override
        public void run() {
            String ip = PropertiesUtil.getProperty("moonbox.netty.server.ip");
            String port = PropertiesUtil.getProperty("moonbox.netty.server.port");
            try {
                BootNettyClient.connect(Integer.parseInt(port), ip);

            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused") || e.getMessage().contains("Connection refused: connect")){
                    executorService.scheduleAtFixedRate(reconnect(), 0, 10, TimeUnit.SECONDS);
                }
                logger.error("连接异常：",e);
            }
        }
    }


public static void main(String[] args) {
    premain("123 789",null);
 }



}
