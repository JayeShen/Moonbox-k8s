package com.vivo.jvm.sandbox.moonbox.plugin.jdk.http.wrapper;

import com.vivo.jvm.sandbox.moonbox.plugin.jdk.http.util.JdkHttpEntity;
import org.apache.commons.io.IOUtils;
import sun.net.www.protocol.http.Handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class RecordTraceHttpWrapper extends JdkHttpConnectionWrapper{
    public RecordTraceHttpWrapper(URL u, Proxy p, Handler handler) throws IOException {
        super(u, p, handler);
    }
/*
    public InputStream getInputStream() throws IOException {
        if (this.inputStreamHasGet) {
            return this.customInputStream;
        } else if (this.isInGetInputStream) {
            return super.getInputStream0();
        } else {
*//*            if (!this.isTraceStarted) {
                Pradar.startClientInvoke(this.serviceName, this.methodName);
                this.isTraceStarted = true;
            }*//*

            InvokeContext context = Pradar.getInvokeContext();
            if (context != null && context.getRecordedTraceId() == null) {
                if (!this.isHeaderSetted) {
                    JdkHttpUtils.setPradarParams(this);
                    this.isHeaderSetted = true;
                }

                this.isRecording = true;
                this.entity = new JdkHttpEntity();
                this.isInGetInputStream = true;
                InputStream in = super.getInputStream0();
                this.isInGetInputStream = false;
                this.inputStreamHasGet = true;
                Map<String, List<String>> headerFields = super.getHeaderFields0();
                LOGGER.info("headerFields=" + headerFields);
                if (this.headerFields == null) {
                    this.initHeaders(headerFields);
                    LOGGER.info("headerFields=" + headerFields);
                }

                if (headerFields.containsKey(this.HEADER_CONTENT_ENCODING) && headerFields.get(this.HEADER_CONTENT_ENCODING) != null) {
                    List<String> strings = (List)headerFields.get(this.HEADER_CONTENT_ENCODING);
                    if (((String)strings.get(0)).equals(this.CONTENT_ENCODING_GZIP)) {
                        in = new GZIPInputStream((InputStream)in);
                        this.headerFields.remove(this.HEADER_CONTENT_ENCODING);
                    }
                }

                this.entity.setHeaderFields(this.headerFields);
               // boolean unKnownApp = UnKnownAppUtils.whetherUnKnownApp(headerFields, this.url.toString());
                if (unKnownApp) {
                    String encoding = "UTF-8";
*//*                    if (super.getContentType0() != null && JdkHttpConstants.streamContentType.contains(super.getContentType0())) {
                        encoding = "ISO-8859-1";
                    }*//*

                    String result = IOUtils.toString((InputStream)in, encoding);
                    ((InputStream)in).close();
                    this.customInputStream = new ByteArrayInputStream(result.getBytes(encoding));
                    this.entity.setBody(result);
                    int code = super.getResponseCode0();
                    this.entity.setCode(code);
                    this.entity.setIsRecord(true);
                } else {
                    this.customInputStream = (InputStream)in;
                    this.entity.setIsRecord(false);
                    int code = super.getResponseCode0();
                    this.entity.setCode(code);
                }

                //Pradar.response((new Gson()).toJson(this.entity));
            }

            *//*Pradar.middlewareName(JdkHttpConstants.MIDDLEWARE_NAME);
            Pradar.remoteIp(this.remoteIp);
            Pradar.endClientInvoke("00", 1);*//*
            return this.customInputStream;
        }
    }*/
}
