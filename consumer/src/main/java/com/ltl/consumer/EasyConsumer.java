package com.ltl.consumer;

import com.ltl.consumer.service.UserService;
import com.ltl.rpc.proxy.ServiceProxy;
import com.ltl.rpc.proxy.ServiceProxyFactory;

public class EasyConsumer {
    public static void main(String[] args) {
        // 从RPC获取代理对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        System.out.println(userService.getNickname("zhangsan"));
    }
}
