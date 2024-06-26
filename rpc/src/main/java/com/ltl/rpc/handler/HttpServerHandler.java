package com.ltl.rpc.handler;

import com.ltl.rpc.RpcApplication;
import com.ltl.rpc.model.RpcRequest;
import com.ltl.rpc.model.RpcResponse;
import com.ltl.rpc.register.LocalRegister;
import com.ltl.rpc.serializer.JdkSerializer;
import com.ltl.rpc.serializer.Serializer;
import com.ltl.rpc.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HTTP 请求处理
 * 处理RPC请求
 */
@Slf4j
public class HttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {
        // 指定序列化器
        Serializer serializer = SerializerFactory.getInstance();
        log.info("HttpServerHandler serializer: " + serializer.getClass().getName());

        log.info("Received request: " + request.method() + " " + request.uri());

        request.bodyHandler((body) ->{
            // 反序列化拿到请求对象
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(body.getBytes(), RpcRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            RpcResponse rpcResponse = new RpcResponse();
            // 如果请求对象为空，返回错误响应
            if(Objects.isNull(rpcRequest)){
                rpcResponse.setMessage("rpc request is null...");
                this.doResponse(request, rpcResponse, serializer);
            }

            try {
                // 获取服务实现类，反射调用
                Class<?> implService = LocalRegister.getService(rpcRequest.getServiceName());
                Method method = implService.getDeclaredMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                method.setAccessible(true);
                Object result = method.invoke(implService.getDeclaredConstructor().newInstance(), rpcRequest.getArgs());

                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("success");

            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }

            this.doResponse(request, rpcResponse, serializer);
        });


    }

    public void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer){
        try {
            HttpServerResponse response = request.response().putHeader("Content-Type", "application/json");

            byte[] serialize = serializer.serialize(rpcResponse);
            response.end(Buffer.buffer(serialize));
        }catch (Exception e){
            log.error(String.valueOf(e));
        }

    }
}
