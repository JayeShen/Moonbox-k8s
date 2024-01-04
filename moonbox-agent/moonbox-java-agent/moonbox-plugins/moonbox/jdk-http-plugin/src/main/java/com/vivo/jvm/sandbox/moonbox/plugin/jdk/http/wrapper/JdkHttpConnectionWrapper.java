package com.vivo.jvm.sandbox.moonbox.plugin.jdk.http.wrapper;

import com.vivo.jvm.sandbox.moonbox.plugin.jdk.http.util.JdkHttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.http.Handler;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.URL;
import java.util.*;

public class JdkHttpConnectionWrapper extends HttpURLConnection implements JdkConnectionInterface{
    protected static final Logger LOGGER = LoggerFactory.getLogger(JdkHttpConnectionWrapper.class.getName());
    protected String HEADER_CONTENT_ENCODING = "Content-Encoding";
    protected String CONTENT_ENCODING_GZIP = "gzip";
    protected boolean isInGetInputStream = false;
    protected String methodName;
    protected String remoteIp;
    protected String serviceName;
    protected boolean inputStreamHasGet = false;
    protected InputStream customInputStream;
    protected boolean isRecording = true;
    protected boolean replayDirect = false;
    protected JdkHttpEntity entity;
    protected Map<String, List<String>> headerFields;
    protected boolean isHeaderSetted = false;
    protected boolean isTraceStarted = false;

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getRemoteIp() {
        return this.remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public JdkHttpConnectionWrapper(URL u, Proxy p, Handler handler) throws IOException {
        super(u, p, handler);
    }

    public JdkHttpEntity getEntity() {
        return this.entity;
    }

    public void setEntity(JdkHttpEntity entity) {
        this.entity = entity;
    }

    public void connect() throws IOException {
/*        if (this.isRecording || this.replayDirect) {
            LOGGER.debug("connect");
            if (!this.isHeaderSetted) {
                if (!this.isTraceStarted) {
                    Pradar.startClientInvoke(this.serviceName, this.methodName);
                    this.isTraceStarted = true;
                }

                JdkHttpUtils.setPradarParams(this);
                this.isHeaderSetted = true;
            }*/

            super.connect();


    }

    public int getResponseCode() throws IOException {
        if (this.isInGetInputStream) {
            return super.getResponseCode();
        } else {
            this.getInputStream();
            return this.entity.getCode();
        }
    }

    public int getResponseCode0() throws IOException {
        return super.getResponseCode();
    }

    public String getHeaderField(String name) {
        if (this.headerFields != null) {
            return this.headerFields.get(name) == null ? null : (String)((List)this.headerFields.get(name)).get(0);
        } else if (this.isInGetInputStream) {
            return super.getHeaderField(name);
        } else {
            Map<String, List<String>> headerFields = this.getHeaderFields();
            List<String> list = (List)headerFields.get(name);
            return list != null && list.size() > 0 ? (String)list.get(0) : null;
        }
    }

    public Map<String, List<String>> getHeaderFields() {
        if (this.headerFields != null) {
            return this.cloneHeaderFields(this.headerFields);
        } else if (this.isInGetInputStream) {
            return super.getHeaderFields();
        } else {
            try {
                this.getInputStream();
            } catch (IOException var2) {
                LOGGER.error(var2.getMessage(), var2);
            }

            Map<String, List<String>> headerFields = this.entity.getHeaderFields();
            return this.cloneHeaderFields(headerFields);
        }
    }

    public Map<String, List<String>> getHeaderFields0() {
        return super.getHeaderFields();
    }

    public String getHeaderField(int n) {
        String key;
        if (this.headerFields != null) {
            key = this.getHeaderFieldKey(n);
            List<String> list = (List)this.headerFields.get(key);
            return list != null && list.size() > 0 ? (String)list.get(0) : null;
        } else if (this.isInGetInputStream) {
            return super.getHeaderField(n);
        } else {
            key = this.getHeaderFieldKey(n);
            Map<String, List<String>> headerFields = this.entity.getHeaderFields();
            List<String> list = (List)headerFields.get(key);
            return list != null && list.size() > 0 ? (String)list.get(0) : null;
        }
    }

    public String getHeaderFieldKey(int n) {
        if (this.headerFields != null) {
            return this.getHeaderFromHeaderFields(n);
        } else if (this.isInGetInputStream) {
            return super.getHeaderFieldKey(n);
        } else {
            try {
                this.getInputStream();
            } catch (IOException var3) {
                LOGGER.error(var3.getMessage(), var3);
            }

            return this.getHeaderFromHeaderFields(n);
        }
    }

    Map<String, List<String>> cloneHeaderFields(Map<String, List<String>> headerFields) {
        Map<String, List<String>> headerFields1 = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        Iterator var3 = headerFields.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, List<String>> entry = (Map.Entry)var3.next();
            if (entry.getKey() != null) {
                headerFields1.put(entry.getKey(), entry.getValue());
            }
        }

        return headerFields1;
    }

    String getHeaderFromHeaderFields(int n) {
        Set<String> keySet1 = this.headerFields.keySet();
        List<String> keyList = new ArrayList();
        Iterator var4 = keySet1.iterator();

        while(var4.hasNext()) {
            String key = (String)var4.next();
            if (key != null) {
                keyList.add(key);
            }
        }

        if (n >= 0 && n < keyList.size()) {
            return (String)keyList.get(n);
        } else {
            return null;
        }
    }

    public synchronized OutputStream getOutputStream() throws IOException {
/*        if (!this.isHeaderSetted) {
            if (!this.isTraceStarted) {
                Pradar.startClientInvoke(this.serviceName, this.methodName);
                this.isTraceStarted = true;
            }

            JdkHttpUtils.setPradarParams(this);
            this.isHeaderSetted = true;
        }*/

        return super.getOutputStream();
    }

    public InputStream getInputStream() throws IOException {
        return super.getInputStream();
    }

    public InputStream getInputStream0() throws IOException {
        return super.getInputStream();
    }

    protected void initHeaders(Map<String, List<String>> headerFields) {
        Map<String, List<String>> headerFields1 = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        Iterator var3 = headerFields.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, List<String>> entry = (Map.Entry)var3.next();
            if (entry.getKey() != null) {
                headerFields1.put(entry.getKey(), entry.getValue());
            }
        }

        this.headerFields = headerFields1;
    }

    public void disconnect() {
        LOGGER.debug("disconnect");
        super.disconnect();
    }

    public int getContentLength() {
        String contentLength = this.getHeaderField("content-length");
        return contentLength != null ? Integer.parseInt(contentLength) : -1;
    }

    public long getContentLengthLong() {
        String contentLength = this.getHeaderField("content-length");
        return contentLength != null ? (long)Integer.parseInt(contentLength) : -1L;
    }

    public String getContentType() {
        String contentType = this.getHeaderField("content-type");
        return contentType;
    }

    public String getContentType0() {
        return super.getContentType();
    }

    public String getContentEncoding() {
        return this.getHeaderField("content-encoding");
    }

    public int getHeaderFieldInt(String name, int Default) {
        String value = this.getHeaderField(name);

        try {
            return Integer.parseInt(value);
        } catch (Exception var5) {
            return Default;
        }
    }

    public long getHeaderFieldLong(String name, long Default) {
        String value = this.getHeaderField(name);

        try {
            return Long.parseLong(value);
        } catch (Exception var6) {
            return Default;
        }
    }
}
