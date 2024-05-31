package com.ltl.rpc.config;

import com.ltl.rpc.serializer.Serializer;
import com.ltl.rpc.serializer.SerializerKeys;
import lombok.Data;

@Data
public class RpcConfig {

    private String name = "yi-rpc";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8080;

    private boolean isMock = false;

    private String serializer = SerializerKeys.JDK;
}
