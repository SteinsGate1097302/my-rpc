package com.ltl.rpc.config;

import lombok.Data;

@Data
public class RpcConfig {

    private String name = "yi-rpc";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8080;

    private boolean isMock = false;
}
