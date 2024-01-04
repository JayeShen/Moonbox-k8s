//

// Source code recreated from a .class file by IntelliJ IDEA

// (powered by FernFlower decompiler)

//


package com.vivo.internet.moonbox.web.console;


//import com.cpic.caf.compon.tech.utils.CharsetUtil;

//import com.cpic.caf.compon.tech.utils.URLUtil;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;

import java.io.PrintWriter;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.SocketException;


@Slf4j
public class HttpService {

    // private static String encodeFormat = CharsetUtil.defaultCharsetName();


    public HttpService() {

    }


    public static String doPost(String httpUrl, String param) {

        HttpURLConnection connection = null;

        String result = null;


        try {

            //connection = (HttpURLConnection)URLUtil.url(httpUrl + encodeFormat).openConnection();

            connection.setDoOutput(true);

            connection.setDoInput(true);

            connection.setRequestMethod("POST");

            connection.setUseCaches(false);

            connection.setInstanceFollowRedirects(true);

            connection.setConnectTimeout(71000);

            connection.setReadTimeout(71000);

            connection.setRequestProperty("charset", "utf-8");

            connection.setRequestProperty("Content-Type", "application/json;");

            connection.connect();

            PrintWriter ot = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));

            Throwable var5 = null;


            try {

                ot.println(param);

                ot.flush();

            } catch (Throwable var80) {

                var5 = var80;

                throw var80;

            } finally {

                if (ot != null) {

                    if (var5 != null) {

                        try {

                            ot.close();

                        } catch (Throwable var79) {

                            var5.addSuppressed(var79);

                        }

                    } else {

                        ot.close();

                    }

                }


            }


            int responseCode = connection.getResponseCode();

            if (responseCode >= 200 && responseCode < 300) {

                InputStream is = connection.getInputStream();

                Throwable var6 = null;


                try {

                    BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    Throwable var8 = null;


                    try {

                        StringBuffer sbf = new StringBuffer();

                        String temp = null;


                        while (true) {

                            if ((temp = br.readLine()) == null) {

                                result = sbf.toString();

                                break;

                            }


                            sbf.append(temp);

                        }

                    } catch (Throwable var81) {

                        var8 = var81;

                        throw var81;

                    } finally {

                        if (br != null) {

                            if (var8 != null) {

                                try {

                                    br.close();

                                } catch (Throwable var78) {

                                    var8.addSuppressed(var78);

                                }

                            } else {

                                br.close();

                            }

                        }


                    }

                } catch (Throwable var83) {

                    var6 = var83;

                    throw var83;

                } finally {

                    if (is != null) {

                        if (var6 != null) {

                            try {

                                is.close();

                            } catch (Throwable var77) {

                                var6.addSuppressed(var77);

                            }

                        } else {

                            is.close();

                        }

                    }


                }


                log.info("[http success],responseCode=" + responseCode + ",resvStr=" + result);

            } else {

                log.info("[http fail],responseCode=" + responseCode);

                result = "sdk_error_" + responseCode;

            }

        } catch (MalformedURLException var86) {

            log.error("while get MalformedURLException error~  " + var86);

            result = "sdk_error_mfurl_" + var86;

        } catch (SocketException var87) {

            log.error("[http connect error]  " + var87);

            result = "sdk_error_socket_" + var87;

        } catch (IOException var88) {

            log.error("while get IOException error~  " + var88);

            result = "sdk_error_io_" + var88;

        } finally {

            if (connection != null) {

                connection.disconnect();

            }


        }


        return result;

    }

}

