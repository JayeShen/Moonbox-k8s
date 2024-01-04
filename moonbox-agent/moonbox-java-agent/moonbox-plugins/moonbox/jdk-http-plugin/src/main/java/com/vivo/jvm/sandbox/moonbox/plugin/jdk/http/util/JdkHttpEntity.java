package com.vivo.jvm.sandbox.moonbox.plugin.jdk.http.util;

import java.util.List;
import java.util.Map;

public class JdkHttpEntity{
    private Integer code;
    private String body;
    private Map<String, List<String>> headerFields;
    private Boolean isRecord;

    public JdkHttpEntity() {
    }

    public Boolean getIsRecord() {
        return this.isRecord;
    }

    public void setIsRecord(Boolean isRecord) {
        this.isRecord = isRecord;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, List<String>> getHeaderFields() {
        return this.headerFields;
    }

    public void setHeaderFields(Map<String, List<String>> headerFields) {
        this.headerFields = headerFields;
    }
}
