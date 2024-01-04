package com.vivo.jvm.sandbox.moonbox.proxy.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;

public class HeartbeatClientHandler extends ChannelInboundHandlerAdapter {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(HeartbeatClientHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.WRITER_IDLE) {
                logger.info("20s内没数据交互，发送心跳检测包");
                String content = "Heartbeat"+"\n";
                ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
                ctx.writeAndFlush(byteBuf);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}