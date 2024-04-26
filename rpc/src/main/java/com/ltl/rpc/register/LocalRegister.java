package com.ltl.rpc.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册中心
 */
public class LocalRegister {

    private static final Map<String, Class<?>> serviceMap = new ConcurrentHashMap<>();

    /**
     * 注册服务
     * @param serviceName  服务名称
     * @param serviceClass 服务class类
     */
    public static void register(String serviceName, Class<?> serviceClass) {
        serviceMap.put(serviceName, serviceClass);
    }


    /**
     * 获取服务
     * @param serviceName  服务名称
     * @return 服务class类
     */
    public static Class<?> getService(String serviceName) {
        return serviceMap.get(serviceName);
    }


    /**
     * 注销服务
     * @param serviceName  服务名称
     */
    public static void remove(String serviceName) {
        serviceMap.remove(serviceName);
    }
}
