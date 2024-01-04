package com.vivo.jvm.sandbox.moonbox.proxy.util;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class BootNettyChannelInitializer<SocketChannel> extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        // ChannelOutboundHandler，依照逆序执行(从下往上)
        ch.pipeline().addLast("encoder", new StringEncoder());
        // 属于ChannelInboundHandler，依照顺序执行(从上往下)
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
        ch.pipeline().addLast("decoder", new StringDecoder());
        /**
         * 心跳检测
         */
        ch.pipeline().addLast(new IdleStateHandler(0, 20, 0, TimeUnit.SECONDS));
        ch.pipeline().addLast(new HeartbeatClientHandler());
        /**
         * 自定义ChannelInboundHandlerAdapter
         */
        ch.pipeline().addLast(new BootNettyChannelInboundHandlerAdapter());
    }
}
