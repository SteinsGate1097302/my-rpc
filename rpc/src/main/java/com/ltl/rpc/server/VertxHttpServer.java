package com.ltl.rpc.server;

import com.ltl.rpc.handler.HttpServerHandler;
import io.vertx.core.AbstractVerticle;

public class VertxHttpServer extends AbstractVerticle {
    private final Integer port;

    public VertxHttpServer(Integer port) {
        this.port = port;
    }

    @Override
    public void start() {
        vertx.createHttpServer()
            .requestHandler(new HttpServerHandler())

            .listen(port, result -> {
                if (result.succeeded()) {
                    System.out.println("Server is now listening port: " + port + "!");
                } else {
                    System.err.println("Failed to listen on port " + port);
                }
            });
    }
}
