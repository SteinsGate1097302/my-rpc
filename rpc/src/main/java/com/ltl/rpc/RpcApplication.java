package com.ltl.rpc;

import com.ltl.rpc.config.RpcConfig;
import com.ltl.rpc.constant.RpcConstant;
import com.ltl.rpc.util.ConfigUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    private RpcApplication(){
        // 私有 构造函数
    }

    /**
     * 初始化框架，支持自定义配置
     * @param config
     */
    public static void init(RpcConfig config) {
        rpcConfig = config;
        log.info("RpcApplication init success, config: {}", config);
    }

    /**
     * 初始化框架，读取配置文件
     */
    public static void init() {
        RpcConfig config;
        try {
            config = ConfigUtil.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch (Exception e){
            log.error("RpcApplication init error, {}", e.getMessage());
            config = new RpcConfig();
        }
        init(config);
    }


    /**
     * 双重检查锁 单例模式
     * @return
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
