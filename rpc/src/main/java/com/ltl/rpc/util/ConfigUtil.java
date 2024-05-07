package com.ltl.rpc.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

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
    public static <T> T loadConfig(Class<T> clazz, String prefix, String environment){
        StringBuilder configName = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)){
            configName.append("-").append(environment);
        }
        configName.append(".properties");
        Props props = new Props(configName.toString());

        return props.toBean(clazz, prefix);
    }
}
