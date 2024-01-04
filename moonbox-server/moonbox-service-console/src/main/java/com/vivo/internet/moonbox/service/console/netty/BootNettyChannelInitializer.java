package com.vivo.internet.moonbox.service.console.netty;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BootNettyChannelInitializer<SocketChannel> extends ChannelInitializer<Channel> {

    @Autowired
    private BootNettyChannelInboundHandlerAdapter bootNettyChannelInboundHandlerAdapter;
    @Autowired
    private HeartbeatServerHandler heartbeatServerHandler;
    @Override
    protected void initChannel(Channel ch) throws Exception {
        // ChannelOutboundHandler，依照逆序执行
        ch.pipeline().addLast("encoder", new StringEncoder());
        // 属于ChannelInboundHandler，依照顺序执行
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
        ch.pipeline().addLast("decoder", new StringDecoder());
        /**
         * 心跳检测
         */
        ch.pipeline().addLast(new IdleStateHandler(0, 20, 0, TimeUnit.SECONDS));
        ch.pipeline().addLast(heartbeatServerHandler);
        /**
         * 自定义ChannelInboundHandlerAdapter
         */
//        ch.pipeline().addLast(new BootNettyChannelInboundHandlerAdapter());
        ch.pipeline().addLast(bootNettyChannelInboundHandlerAdapter);


    }
}
