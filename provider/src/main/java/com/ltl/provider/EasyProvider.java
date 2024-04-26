package com.ltl.provider;

import com.ltl.provider.service.UserService;
import com.ltl.provider.service.impl.UserServiceImpl;
import com.ltl.rpc.register.LocalRegister;
import com.ltl.rpc.server.VertxHttpServer;
import io.vertx.core.Vertx;

public class EasyProvider {

    public static void main(String[] args) {
        // 注册服务
        LocalRegister.register(UserService.class.getSimpleName(), UserServiceImpl.class);

        // 启动服务
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new VertxHttpServer(8080));
    }
}
