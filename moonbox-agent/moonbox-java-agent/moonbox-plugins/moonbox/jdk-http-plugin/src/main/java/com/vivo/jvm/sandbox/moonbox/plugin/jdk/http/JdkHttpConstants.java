package com.vivo.jvm.sandbox.moonbox.plugin.jdk.http;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JdkHttpConstants {
    public static final String PLUGIN_NAME = "jdk-http-unknown";
    public static String MIDDLEWARE_NAME = "jdk-http";
    public static final int PLUGIN_TYPE = 1;
    public static final String querySql = "SELECT response FROM t_record_trace where traceId = ? and rpcId = ? and  middlewareName = ?";
    public static Set<String> streamContentType = new HashSet(Arrays.asList("application/pdf", "application/x-img", "image/gif", "image/pnetvue", "image/png", "application/msword", "application/octet-stream", "image/jpeg", "application/x-ppt", "application/pics-rules", "video/mpeg4", "video/mpeg4", "video/mpg"));
    public static final String MARK_HEADER = "FUCE_AGENT_RESP";

    public JdkHttpConstants() {
    }
}
