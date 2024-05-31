package com.ltl.rpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.ltl.rpc.model.RpcRequest;
import com.ltl.rpc.model.RpcResponse;
import com.ltl.rpc.serializer.Serializer;
import com.ltl.rpc.serializer.SerializerFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务代理
 * 将请求发送给对应的 服务提供者
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        Serializer serializer = SerializerFactory.getInstance();
        log.info("ServiceProxy serializer: " + serializer.getClass().getName());

        // 构造请求
        RpcRequest request = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getSimpleName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args).build();

        // 序列化请求
        byte[] bytes = serializer.serialize(request);
        // 发送请求
        // TODO: 这里请求被写死，后续使用注册中心获取服务提供者地址
        try(HttpResponse response = HttpRequest.post("http://localhost:8080").body(bytes).execute()){
            byte[] resultBytes = response.bodyBytes();
            // 反序列化
            RpcResponse rpcResponse = serializer.deserialize(resultBytes, RpcResponse.class);

            return rpcResponse.getData();
        }

    }
}
