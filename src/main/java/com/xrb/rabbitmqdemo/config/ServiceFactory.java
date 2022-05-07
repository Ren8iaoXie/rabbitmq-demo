package com.xrb.rabbitmqdemo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xieren8iao
 * @date 2022/4/21 8:57 下午
 */
public class ServiceFactory {
    static Properties properties;

    static Map<Class<?>,Object> map=new ConcurrentHashMap<>();

    static {
        try (InputStream in=ServiceFactory.class.getResourceAsStream("/application.yaml")){
            properties=new Properties();
            properties.load(in);
            Set<String> names=properties.stringPropertyNames();
            for (String name : names) {
                if (name.endsWith("Service")){
                    Class<?> interfaceClass = Class.forName(name);
                    Class<?> instanceClass = Class.forName(properties.getProperty(name));
                    map.put(interfaceClass,instanceClass.newInstance());
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}