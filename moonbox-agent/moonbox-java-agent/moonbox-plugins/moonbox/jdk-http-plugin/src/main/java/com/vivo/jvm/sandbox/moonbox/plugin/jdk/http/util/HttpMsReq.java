package com.vivo.jvm.sandbox.moonbox.plugin.jdk.http.util;

public class HttpMsReq {
    private String serviceName;
    private String reqIdentification;
    private String serviceIdentification;
    private String reqOrganizationNo;
    private String reqTellerNo;
    private String versionNum;
    private String outBusCode;
    private String sdkKey;
    private String sdkIp;
    private String mainSendMessage;
    private String encodeFormat;

    public HttpMsReq() {
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getReqIdentification() {
        return this.reqIdentification;
    }

    public String getServiceIdentification() {
        return this.serviceIdentification;
    }

    public String getReqOrganizationNo() {
        return this.reqOrganizationNo;
    }

    public String getReqTellerNo() {
        return this.reqTellerNo;
    }

    public String getVersionNum() {
        return this.versionNum;
    }

    public String getOutBusCode() {
        return this.outBusCode;
    }

    public String getSdkKey() {
        return this.sdkKey;
    }

    public String getSdkIp() {
        return this.sdkIp;
    }

    public String getMainSendMessage() {
        return this.mainSendMessage;
    }

    public String getEncodeFormat() {
        return this.encodeFormat;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setReqIdentification(String reqIdentification) {
        this.reqIdentification = reqIdentification;
    }

    public void setServiceIdentification(String serviceIdentification) {
        this.serviceIdentification = serviceIdentification;
    }

    public void setReqOrganizationNo(String reqOrganizationNo) {
        this.reqOrganizationNo = reqOrganizationNo;
    }

    public void setReqTellerNo(String reqTellerNo) {
        this.reqTellerNo = reqTellerNo;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public void setOutBusCode(String outBusCode) {
        this.outBusCode = outBusCode;
    }

    public void setSdkKey(String sdkKey) {
        this.sdkKey = sdkKey;
    }

    public void setSdkIp(String sdkIp) {
        this.sdkIp = sdkIp;
    }

    public void setMainSendMessage(String mainSendMessage) {
        this.mainSendMessage = mainSendMessage;
    }

    public void setEncodeFormat(String encodeFormat) {
        this.encodeFormat = encodeFormat;
    }

    public String toString() {
        return "HttpMsReq [serviceName=" + this.serviceName + ", reqIdentification=" + this.reqIdentification + ", serviceIdentification=" + this.serviceIdentification + ", reqOrganizationNo=" + this.reqOrganizationNo + ", reqTellerNo=" + this.reqTellerNo + ", versionNum=" + this.versionNum + ", outBusCode=" + this.outBusCode + ", sdkKey=" + this.sdkKey + ", sdkIp=" + this.sdkIp + ", mainSendMessage=" + this.mainSendMessage + ", encodeFormat=" + this.encodeFormat + "]";
    }
}
