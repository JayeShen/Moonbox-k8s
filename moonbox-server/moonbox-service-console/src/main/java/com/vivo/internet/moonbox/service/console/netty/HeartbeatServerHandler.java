package com.vivo.internet.moonbox.service.console.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable//一个server接受多个client，但需要保证线程安全
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(BootNettyChannelInboundHandlerAdapter.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {

                throw new Exception("idle check fail!");
            } else if (e.state() == IdleState.WRITER_IDLE) {
                // 发送心跳包
                String content = "Heartbeat"+"\n";
                logger.info("20s内没数据交互，发送心跳检测包");
                ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
                ctx.writeAndFlush(byteBuf);
            }
        }
    }
}