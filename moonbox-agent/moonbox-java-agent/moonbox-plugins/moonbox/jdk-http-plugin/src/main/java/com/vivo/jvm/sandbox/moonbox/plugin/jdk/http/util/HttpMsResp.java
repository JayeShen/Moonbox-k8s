package com.vivo.jvm.sandbox.moonbox.plugin.jdk.http.util;

public class HttpMsResp {
    private String sdkKey;
    private String apiIp;
    private String retStr;
    private String isAsyn;
    private String status;
    private String errormsg;

    public HttpMsResp() {
    }

    public String getSdkKey() {
        return this.sdkKey;
    }

    public String getApiIp() {
        return this.apiIp;
    }

    public String getRetStr() {
        return this.retStr;
    }

    public String getIsAsyn() {
        return this.isAsyn;
    }

    public String getStatus() {
        return this.status;
    }

    public String getErrormsg() {
        return this.errormsg;
    }

    public void setSdkKey(String sdkKey) {
        this.sdkKey = sdkKey;
    }

    public void setApiIp(String apiIp) {
        this.apiIp = apiIp;
    }

    public void setRetStr(String retStr) {
        this.retStr = retStr;
    }

    public void setIsAsyn(String isAsyn) {
        this.isAsyn = isAsyn;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String toString() {
        return "HttpMsResp [sdkKey=" + this.sdkKey + ", apiIp=" + this.apiIp + ", retStr=" + this.retStr + ", isAsyn=" + this.isAsyn + ", status=" + this.status + ", errormsg=" + this.errormsg + "]";
    }
}
