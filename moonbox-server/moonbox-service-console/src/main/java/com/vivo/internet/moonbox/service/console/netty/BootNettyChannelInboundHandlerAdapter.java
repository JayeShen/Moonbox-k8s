package com.vivo.internet.moonbox.service.console.netty;

import com.vivo.internet.moonbox.service.console.AppServerService;
import com.vivo.internet.moonbox.service.console.vo.ActiveAppServerHostInfoVo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
@ChannelHandler.Sharable//一个server接受多个client，但需要保证线程安全
public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(BootNettyChannelInboundHandlerAdapter.class);

    @Autowired
    private AppServerService appServerService;

    private static final Map<String, Channel> channelMap = new ConcurrentHashMap<>();
    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception, IOException {
        //心跳处理
        if (msg.toString().contains("Heartbeat")){
            logger.info("心跳检测，ClientIP："+ctx.channel().remoteAddress());
            return;
        }
        logger.info("获取到客户端"+ctx.channel().remoteAddress()+"请求的数据：" + msg);
        ctx.channel().read();
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        // 在连接建立时，将ChannelId和Channel关联起来并保存在Map中
        Channel channel = ctx.channel();
        String channelId = channel.id().asLongText(); // 或者使用asShortText()
        logger.info("连接中channelId = " + channelId);
        channelMap.put(channelId, channel);
        /**
         * 落表
         */
        Map<String, String> map = convert(msg.toString());
        logger.info("map = " + map);
        String appName =map.get("appName");
        String systemCode =map.get("systemCode");
        String systemName =map.get("systemName");
        String moonBoxAppEnvCode = map.get("env");
        //查询之前是否有记录
        ActiveAppServerHostInfoVo activeAppServerHostInfoVo = new ActiveAppServerHostInfoVo();
        activeAppServerHostInfoVo.setAppName(appName);
        activeAppServerHostInfoVo.setSystemCode(systemCode);
        activeAppServerHostInfoVo.setSystemName(systemName);
        activeAppServerHostInfoVo.setIp(clientIp);
        activeAppServerHostInfoVo.setAppEnv(moonBoxAppEnvCode);

        List<ActiveAppServerHostInfoVo> activeAppServerHostInfoVos1 = appServerService.selectAppServerHostInfoList(activeAppServerHostInfoVo);
        if (activeAppServerHostInfoVos1.size()>0){
            //已存在，更新上线状态以及管道id
            ActiveAppServerHostInfoVo activeAppServerHostInfoVo1 = activeAppServerHostInfoVos1.get(0);
            activeAppServerHostInfoVo1.setChannelId(channelId);
            activeAppServerHostInfoVo1.setOnlineState(1);
            activeAppServerHostInfoVo1.setAppEnv(moonBoxAppEnvCode);
            appServerService.updateAppServerHostInfo(activeAppServerHostInfoVo1);
        }else {
            //不存在，插入
            activeAppServerHostInfoVo.setChannelId(channelId);
            activeAppServerHostInfoVo.setOnlineState(1);
            activeAppServerHostInfoVo.setCreator("admin");
            appServerService.insertAppServerHostInfo(activeAppServerHostInfoVo);
        }

    }

    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
        //4
        logger.info("连接完成"+ctx.channel().remoteAddress());
        ctx.flush();
    }


    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws IOException {
        //断开连接
        logger.info("连接异常"+ctx.channel().remoteAddress());
        cause.printStackTrace();
        ctx.close();//抛出异常，断开与客户端的连接
    }


    /**
     * 客户端与服务端第一次建立连接时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception, IOException {
        logger.info("首次建立连接"+ctx.channel().remoteAddress());
        //2
        super.channelActive(ctx);

    }


    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        logger.info("连接断开"+ctx.channel().remoteAddress());
        super.channelInactive(ctx);
        // 在连接断开时，从Map中移除ChannelId
        Channel channel = ctx.channel();
        String channelId = channel.id().asLongText(); // 或者使用asShortText()
        channelMap.remove(channelId);
        logger.info("本地缓存移除channelId = " + channelId);
        ActiveAppServerHostInfoVo activeAppServerHostInfoVo = new ActiveAppServerHostInfoVo();
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();

        //更新在线状态
        //先查询
        activeAppServerHostInfoVo.setIp(clientIp);
        activeAppServerHostInfoVo.setChannelId(channelId);
        List<ActiveAppServerHostInfoVo> activeAppServerHostInfoVos1 = appServerService.selectAppServerHostInfoListFromChannelId(activeAppServerHostInfoVo);
        ActiveAppServerHostInfoVo activeAppServerHostInfoVo1 = activeAppServerHostInfoVos1.get(0);
        activeAppServerHostInfoVo1.setCreator("admin");
        activeAppServerHostInfoVo1.setOnlineState(0);
        appServerService.updateAppServerHostInfo(activeAppServerHostInfoVo1);
        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
    }


    /**
     * 服务端当read超时, 会调用这个方法
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception, IOException {
        logger.info("连接超时"+ctx.channel().remoteAddress());
        //超时
        super.userEventTriggered(ctx, evt);
        ctx.close();//超时时断开连接
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //1
        logger.info("客户端已上线"+ctx.channel().remoteAddress());
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端已下线"+ctx.channel().remoteAddress());
    }


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        logger.info("通道可写性已更改"+ctx.channel().remoteAddress());
    }

    // 通过ChannelId获取对应的Channel
    public Channel getChannel(String channelId) {
        return channelMap.get(channelId);
    }

    public static Map<String, String> convert(String input) {
        // Remove the curly braces and split by commas
        String[] keyValuePairs = input.substring(1, input.length() - 1).split(", ");

        // Create a Map to store the key-value pairs
        Map<String, String> resultMap = new HashMap<>();

        for (String pair : keyValuePairs) {
            // Split each key-value pair by '='
            String[] keyValue = pair.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                resultMap.put(key, value);
            } else {
                // Handle invalid input here, e.g., skip or throw an exception
                System.err.println("Invalid key-value pair: " + pair);
            }
        }

        return resultMap;
    }

}
