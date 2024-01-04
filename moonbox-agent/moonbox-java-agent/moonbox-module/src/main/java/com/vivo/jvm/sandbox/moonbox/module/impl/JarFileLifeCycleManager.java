/*
This code comes from the jvm-sandbox-repeater(link:https://github.com/alibaba/jvm-sandbox-repeater) project.
 */
package com.vivo.jvm.sandbox.moonbox.module.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.jvm.sandbox.repeater.plugin.api.LifecycleManager;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.StrategyProvider;
import com.alibaba.jvm.sandbox.repeater.plugin.spi.InvokePlugin;
import com.alibaba.jvm.sandbox.repeater.plugin.spi.Repeater;
import com.google.common.collect.Lists;
import com.vivo.jvm.sandbox.moonbox.module.classloader.PluginClassLoader;
import com.vivo.jvm.sandbox.moonbox.module.utils.SPILoader;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * @author zhaoyb1990
 */
@Slf4j
public class JarFileLifeCycleManager implements LifecycleManager {

    private final static String JAR_FILE_SUFFIX = ".jar";

    private final PluginClassLoader classLoader;

    public JarFileLifeCycleManager(String jarFilePath, PluginClassLoader.Routing... routingArray) {
        File file = new File(jarFilePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("jar file does not exist, path=" + jarFilePath);
        }
        final URL[] urLs = getURLs(jarFilePath);
        if (urLs.length == 0) {
            throw new IllegalArgumentException("does not have any available jar in path:" + jarFilePath);
        }
        this.classLoader = new PluginClassLoader(getURLs(jarFilePath), this.getClass().getClassLoader(), routingArray);
    }

    @Override
    public List<InvokePlugin> loadInvokePlugins() {
        return SPILoader.loadSPI(InvokePlugin.class, classLoader);
    }

    @Override
    public List<Repeater> loadRepeaters() {
        return SPILoader.loadSPI(Repeater.class, classLoader);
    }



    @Override
    public void initMockStrategyRoute(){
        StrategyProvider.initStrategyCache(classLoader);
    }

    @Override
    public void release() {
        classLoader.closeIfPossible();
    }

    /**
     * 获取模块jar的urls
     *
     * @param jarFilePath
     *            插件路径
     * @return 插件URL列表
     */
    private URL[] getURLs(String jarFilePath) {
        File file = new File(jarFilePath);
        List<URL> jarPaths = Lists.newArrayList();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return jarPaths.toArray(new URL[0]);
            }
            for (File jarFile : files) {
                if (isJar(jarFile)) {
                    try {
                        File tempFile = File.createTempFile("repeater_plugin", ".jar");
                        tempFile.deleteOnExit();
                        FileUtils.copyFile(jarFile, tempFile);
                        jarPaths.add(new URL("file:" + tempFile.getPath()));
                    } catch (IOException e) {
                        log.error("error occurred when get jar file", e);
                    }
                } else {
                    jarPaths.addAll(Arrays.asList(getURLs(jarFile.getAbsolutePath())));
                }
            }
        } else if (isJar(file)) {
            try {
                File tempFile = File.createTempFile("repeater_plugin", ".jar");
                FileUtils.copyFile(file, tempFile);
                jarPaths.add(new URL("file:" + tempFile.getPath()));
            } catch (IOException e) {
                log.error("error occurred when get jar file", e);
            }
            return jarPaths.toArray(new URL[0]);
        } else {
            log.error("plugins jar path has no available jar, use empty url, path={}", jarFilePath);
        }
        return jarPaths.toArray(new URL[0]);
    }

    /**
     * @param file
     * @return
     */
    private boolean isJar(File file) {
        return file.isFile() && file.getName().endsWith(JAR_FILE_SUFFIX);
    }
}
