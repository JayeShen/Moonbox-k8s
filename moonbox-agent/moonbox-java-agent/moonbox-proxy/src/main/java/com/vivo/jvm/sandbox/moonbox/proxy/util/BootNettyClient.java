package com.vivo.jvm.sandbox.moonbox.proxy.util;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;

public class BootNettyClient {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(BootNettyClient.class);


    static int count=1;

    public static void connect(int port, String host) throws Exception {
        /**
         * 客户端的NIO线程组
         *
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            /**
             * Bootstrap 是一个启动NIO服务的辅助启动类 客户端的
             */
            Bootstrap bootstrap = new Bootstrap();
            /**
             * 设置group
             */
            bootstrap = bootstrap.group(group);
            /**
             * 关联客户端通道
             */
            bootstrap = bootstrap.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
            /**
             * 设置 I/O处理类,主要用于网络I/O事件，记录日志，编码、解码消息
             */
            bootstrap = bootstrap.handler(new BootNettyChannelInitializer<SocketChannel>());
            /**
             * 连接服务端
             */
            logger.info("第"+ count++ +"次连接netty-server端");
            ChannelFuture f = bootstrap.connect(host, port).sync();

            /**
             * 等待连接端口关闭
             */
            f.channel().closeFuture().sync();
        } finally {
            /**
             * 退出，释放资源
             */
           group.shutdownGracefully();
        }

    }

}
