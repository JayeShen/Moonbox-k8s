/*
Copyright 2022 vivo Communication Technology Co., Ltd.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.vivo.internet.moonbox.service.common.utils;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * {@link HttpUtil} 基于{@link OkHttpClient}封装的http请求工具
 * <p>
 *
 * @author zhaoyb1990
 */
public class HttpUtil {

    private static final String QUESTION_SEPARATE = "?";

    private static final String PARAM_SEPARATE = "&";

    private static final String KV_SEPARATE = "=";

    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    /**
     * 执行GET请求，返回body的string
     *
     * @param url 请求URL
     * @return response的内容
     */
    public static Resp doGetWithHeader(String url, Map<String, String> headers) {
        final Request.Builder builder = new Request.Builder().get().url(url);
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        return executeRequest(builder.build());
    }

    /**
     * 执行GET请求，返回body的string
     *
     * @param url 请求URL
     * @return response的内容
     */
    public static Resp doGet(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder urlBuilder = new StringBuilder(url);
        if (!StringUtils.contains(url, QUESTION_SEPARATE)) {
            urlBuilder.append(QUESTION_SEPARATE).append("_r=1");
        }
        if (MapUtils.isNotEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.append(PARAM_SEPARATE)
                        .append(entry.getKey())
                        .append(KV_SEPARATE)
                        .append(entry.getValue());
            }
        }

        final Request.Builder builder = new Request.Builder().get().url(urlBuilder.toString());
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }

        return executeRequest(builder.build());
    }

    /**
     * 调用回放方法
     *
     * @param url       url地址
     * @param method    请求方式 POST/GET
     * @param headers   请求头
     * @param paramsMap 请求参数
     * @return Resp
     */
    public static Resp invoke(String url,
                              String method,
                              Map<String, String> headers,
                              Map<String, String[]> paramsMap,
                              String body) {
        HttpMethod resolve = HttpMethod.resolve(method);

        if (resolve == null) {
            return Resp.builder().code(500).message("Unsupported http method : " + method).build();
        }

        switch (HttpMethod.resolve(method)) {
            case GET:
                return invokeGet(url, headers, paramsMap, 0);
            case POST:
                return invokePost(url, headers, paramsMap, body, 0);
            case PUT:
            case HEAD:
            case PATCH:
            case DELETE:
            case OPTIONS:
            case TRACE:
            default:
                return Resp.builder().code(500).message("Unsupported http method : " + method).build();
        }
    }

    /**
     * Get方法请求
     *
     * @param url       url地址
     * @param headers   请求头
     * @param paramsMap 请求参数
     * @return resp
     */
    private static Resp invokeGet(String url,
                                  Map<String, String> headers,
                                  Map<String, String[]> paramsMap, int retryTime) {
        HttpUrl hu = HttpUrl.parse(url);
        if (hu == null) {
            return Resp.builder().code(500).message("Parse http url failed,url=" + url).build();
        }
        if (MapUtils.isNotEmpty(paramsMap)) {
            HttpUrl.Builder builder = hu.newBuilder();
            for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
                for (String value : entry.getValue()) {
                    builder.addQueryParameter(entry.getKey(), value);
                }
            }
            hu = builder.build();
        }
        Request.Builder rb = new Request.Builder().get().url(hu);
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                rb.header(entry.getKey(), entry.getValue());
            }
        }
        return executeRequest(rb.build(), retryTime);
    }

    /**
     * Post方法请求
     *
     * @param url       url地址
     * @param headers   请求头
     * @param paramsMap 请求参数
     * @return Resp
     */
    private static Resp invokePost(String url,
                                   Map<String, String> headers,
                                   Map<String, String[]> paramsMap,
                                   String body,
                                   int retryTime) {
        if (StringUtils.isNotEmpty(body)) {
            return invokePostBody(url, headers, paramsMap, body);
        }
        FormBody.Builder fb = new FormBody.Builder();
        if (MapUtils.isNotEmpty(paramsMap)) {
            for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
                for (String value : entry.getValue()) {
                    fb.add(entry.getKey(), value);
                }
            }
        }
        Request.Builder rb = new Request.Builder().post(fb.build()).url(url);
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                rb.header(entry.getKey(), entry.getValue());
            }
        }
        return executeRequest(rb.build(), retryTime);
    }


    /**
     * Post方法请求
     *
     * @param url     url地址
     * @param headers 请求头
     * @param body    请求body
     * @return resp
     */
    public static Resp invokePostBody(String url,
                                      Map<String, String> headers,
                                      String body) {
        return invokePostBody(url, headers, null ,body);
    }

    /**
     * Post方法请求
     *
     * @param url     url地址
     * @param headers 请求头
     * @param paramMap 请求参数
     * @param body    请求body
     * @return resp
     */
    public static Resp invokePostBody(String url,
                                      Map<String, String> headers,
                                      Map<String, String[]> paramMap,
                                      String body) {
        String contentType = headers.get("Content-Type");
        if (contentType == null) {
            contentType = headers.get("content-type");
        }
        if (contentType == null) {
            contentType = "application/x-www-form-urlencoded; charset=utf-8";
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        // fix issue #43
        if (MapUtils.isNotEmpty(paramMap)) {
            if (!StringUtils.contains(url, QUESTION_SEPARATE)) {
                urlBuilder.append(QUESTION_SEPARATE).append("_r=1");
            }
            for (Map.Entry<String,String[]> entry : paramMap.entrySet()) {
                for (String value : entry.getValue()) {
                    urlBuilder.append(PARAM_SEPARATE)
                            .append(entry.getKey())
                            .append(KV_SEPARATE)
                            .append(value);
                }
            }
        }
        RequestBody b = RequestBody.create(MediaType.parse(contentType), body);
        Request.Builder rb = new Request.Builder().post(b).url(urlBuilder.toString());
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                rb.header(entry.getKey(), entry.getValue());
            }
        }
        return executeRequest(rb.build());
    }

    public static String getPureUrL(Object scheme, Object host){
        return String.valueOf(scheme) + "://" + String.valueOf(host);
    }

    public static Map<String, String> getParamMap(String paramStr){

        Map<String, String> paramMap = new HashMap<String, String>();
        if (StringUtils.isBlank(paramStr) || "null".equalsIgnoreCase(paramStr)){
            return paramMap;
        }

        String[] split = paramStr.split(PARAM_SEPARATE);
        if (split.length == 0){
            return paramMap;
        }

        for (String s : split) {
            String[] param = s.split(KV_SEPARATE);
            if (param.length == 0) {
                continue;
            }
            paramMap.put(param[0], param[1]);
        }

        return paramMap;
    }

    /**
     * 执行request
     *
     * @param request 请求
     * @return body字符串
     */
    public static Resp executeRequest(Request request) {
        return executeRequest(request, 3);
    }

    /**
     * 执行request;
     *
     * @param request   请求
     * @param retryTime 重试次数
     * @return body字符串
     */
    private static Resp executeRequest(Request request, int retryTime) {
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return Resp.builder().code(response.code())
                        .body(bodyToString(response.body())).build();
            }
            if (--retryTime > 0) {
                TimeUnit.MILLISECONDS.sleep(100);
                return executeRequest(request, retryTime);
            }
            return Resp.builder().code(response.code())
                    .body(bodyToString(response.body()))
                    .message("Invoke failed, status code is not 200, responseCode : " + response.code())
                    .build();
        } catch (Exception e) {
            if (--retryTime > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                    // ignore
                }
                return executeRequest(request, retryTime);
            }
            return Resp.builder().code(500)
                    .message("Invoke occurred exception, request=" + request.toString() + ";message=" + e.getMessage())
                    .build();
        }
    }

    private static String bodyToString(ResponseBody body) throws IOException {
        return body == null ? "" : body.string();
    }

    enum HttpMethod {

        /**
         *
         */
        GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

        private static final Map<String, HttpMethod> CACHED = new HashMap<String, HttpMethod>(16);

        static {
            for (HttpMethod httpMethod : values()) {
                CACHED.put(httpMethod.name(), httpMethod);
            }
        }


        public static HttpMethod resolve(String method) {
            return (method != null ? CACHED.get(method) : null);
        }

        public boolean matches(String method) {
            return (this == resolve(method));
        }
    }

    public static class Resp {
        private int code;
        private String body;
        private String message;

        @ConstructorProperties({"code", "body", "message"})
        Resp(int code, String body, String message) {
            this.code = code;
            this.body = body;
            this.message = message;
        }

        public static HttpUtil.Resp.RespBuilder builder() {
            return new HttpUtil.Resp.RespBuilder();
        }

        public boolean isSuccess() {
            return code >= 200 && code < 400;
        }

        public int getCode() {
            return this.code;
        }

        public String getBody() {
            return this.body;
        }

        public String getMessage() {
            return this.message;
        }

        @Override
        public String toString() {
            return "HttpUtil.Resp(code=" + this.getCode() + ", body=" + this.getBody() + ", message=" + this.getMessage() + ")";
        }

        public static class RespBuilder {
            private int code;
            private String body;
            private String message;

            RespBuilder() {
            }

            public HttpUtil.Resp.RespBuilder code(int code) {
                this.code = code;
                return this;
            }

            public HttpUtil.Resp.RespBuilder body(String body) {
                this.body = body;
                return this;
            }

            public HttpUtil.Resp.RespBuilder message(String message) {
                this.message = message;
                return this;
            }

            public HttpUtil.Resp build() {
                return new HttpUtil.Resp(this.code, this.body, this.message);
            }

            @Override
            public String toString() {
                return "HttpUtil.Resp.RespBuilder(code=" + this.code + ", body=" + this.body + ", message=" + this.message + ")";
            }
        }
    }
}
