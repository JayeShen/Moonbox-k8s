package com.vivo.jvm.sandbox.moonbox.proxy.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(BootNettyChannelInboundHandlerAdapter.class);

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private static boolean connected = false;
    /**
     * 从服务端收到新的数据时，这个方法会在收到消息时被调用
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception, IOException {

        //心跳处理
        if (msg.toString().contains("Heartbeat")){
            logger.info("心跳检测，ServerIP："+ctx.channel().remoteAddress());
            return;
        }

        logger.info("访问moonbox服务端，入参" + msg);
        String pre = "bash ~/.sandbox-module/bin/start-remote-agent.sh";
        String command=pre+" "+msg;
        logger.info("command = " + command);
        //解决%问题
        command = command.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        String result = ExecuteShellCommandUtil.executeCommand(command);
        logger.info("命令执行结果:\n" + result);


    }


    /**
     * 从服务端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
        logger.info("连接建立完成"+ctx.channel().remoteAddress());
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
        connected=true;
        logger.info("netty client start success!");
        logger.info("首次建立连接"+ctx.channel().remoteAddress());
        super.channelActive(ctx);
        Map<String, String> map = new HashMap<>();
//        String appName = "base-proxy";
//        String systemCode = "base";
//        String systemName = "基础服务";
//        String MoonBoxAppEnvCode = "sit";
//        map.put("appName",appName);
//        map.put("systemCode",systemCode);
//        map.put("systemName",systemName);
//        map.put("MoonBoxAppEnvCode",MoonBoxAppEnvCode);
        map.put("appName",System.getProperty("appName"));
        map.put("systemCode",System.getProperty("systemCode"));
        map.put("systemName",System.getProperty("systemName"));
        map.put("MoonBoxAppEnvCode","env");
        logger.info("客户端请求信息：" + map);
        ByteBuf byteBuf = Unpooled.copiedBuffer(map+"\n", CharsetUtil.UTF_8);
        //回应服务端
        ctx.writeAndFlush(byteBuf);
    }


    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        connected=false;
        logger.info("连接中断"+ctx.channel().remoteAddress());
        super.channelInactive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
       // ctx.close(); //断开连接时，必须关闭，否则造成资源浪费
        logger.info("channelInactive:" + clientIp);

        //重新连接
        // 定期重新连接
        executorService.scheduleAtFixedRate(reconnect(), 0, 10, TimeUnit.SECONDS);
    }

    public static Runnable reconnect() {
        String ip = PropertiesUtil.getProperty("moonbox.netty.server.ip");
        int port = Integer.valueOf(PropertiesUtil.getProperty("moonbox.netty.server.port"));
        return new Runnable() {
            @Override
            public void run() {
                if (!connected) {
                    try {
                        long currentTimeMillis = System.currentTimeMillis();
                        // Using java.util.Date
                        Date date = new Date(currentTimeMillis);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = sdf.format(date);
                        logger.info(formattedDate);
                        // Attempt to reconnect to the server
                        logger.info("尝试重新连接netty-server");

                        BootNettyClient.connect(port, ip);
                    } catch (Exception e) {
                        // Handle exceptions
                        e.printStackTrace();
                    }
                }else {
                    stopClient();
                    logger.info("停止重连任务");
                }
            }
        };
    }

    public static void stopClient() {
        // 当不再需要客户端时清理资源
        executorService.shutdown();
    }

}
