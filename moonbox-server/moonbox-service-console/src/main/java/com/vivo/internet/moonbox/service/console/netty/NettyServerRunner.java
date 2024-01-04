package com.vivo.internet.moonbox.service.console.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class NettyServerRunner implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(NettyServerRunner.class);

    @Autowired
    private NettyTcpServer nettyTcpServer;

    @Value("${moonbox.netty.server.port}")
    private int port;

    @Override
    public void run(String... args) throws Exception {
        try {
            logger.info("netty服务启动，端口："+port);
            nettyTcpServer.bind(port);
        } catch (Exception e) {
            // TODO：处理异常
            e.printStackTrace();
        }
    }
}
