package com.ltl.rpc.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

    /**
     * 加载配置
     * @param clazz   配置类
     * @param prefix  配置前缀
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> clazz, String prefix){
        return loadConfig(clazz, prefix, "");
    }

    /**
     * 加载配置，支持环境配置
     * @param clazz         配置类
     * @param prefix        配置前缀
     * @param environment   环境名称
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> clazz, String prefix, String environment) {
        StringBuilder configName = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configName.append("-").append(environment);
        }

        String[] fileFormats = {"properties", "yaml", "yml"};
        Props props = null;
        // 遍历读取支持的配置文件
        for (String format : fileFormats) {
            String fileName = configName + "." + format;
            try (InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream(fileName)) {
                if (input != null) {
                    if ("properties".equalsIgnoreCase(format)) {
                        props = new Props(fileName);
                    } else if ("yaml".equalsIgnoreCase(format) || "yml".equalsIgnoreCase(format)) {
                        Yaml yaml = new Yaml();
                        props = yaml.loadAs(input, Props.class);    // yml配置文件处理
                    }
                    if (props != null) {
                        break;
                    }
                }
            } catch (IOException e) {
                // 处理异常
            }
        }

        if (props == null) {
            throw new IllegalArgumentException("No supported configuration file found");
        }

        return props.toBean(clazz, prefix);
    }

}
