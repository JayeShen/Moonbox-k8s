package com.vivo.jvm.sandbox.moonbox.plugin.jdk.http.wrapper;

public interface JdkConnectionInterface {
    String getServiceName();

    void setServiceName(String var1);

    String getMethodName();

    void setMethodName(String var1);

    String getRemoteIp();

    void setRemoteIp(String var1);
}
