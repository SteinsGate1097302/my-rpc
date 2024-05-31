package com.ltl.rpc.serializer;

import com.ltl.rpc.RpcApplication;

import java.util.ServiceLoader;

public class SerializerFactory {

   private static volatile Serializer serializer;

    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    public static Serializer getInstance(){
        if (serializer == null){
            synchronized (SerializerFactory.class){
                if (serializer == null){
                    ServiceLoader<Serializer> serviceLoader = ServiceLoader.load(Serializer.class);
                    String serializerConf = RpcApplication.getRpcConfig().getSerializer();
                    for (Serializer s : serviceLoader) {
                        if (s.getClass().getName().contains(serializerConf)){
                            serializer = s;
                            return serializer; // serviceLoader找到实现类，直接返回
                        }
                    }
                    serializer = DEFAULT_SERIALIZER; // serviceLoader没找到实现类，使用默认实现类
                }
            }
        }
        return serializer;
    }

}
