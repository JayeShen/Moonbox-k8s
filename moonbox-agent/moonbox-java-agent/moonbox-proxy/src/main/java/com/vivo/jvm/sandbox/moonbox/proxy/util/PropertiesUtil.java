package com.vivo.jvm.sandbox.moonbox.proxy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = PropertiesUtil.class.getResourceAsStream("/moonbox-proxy.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
