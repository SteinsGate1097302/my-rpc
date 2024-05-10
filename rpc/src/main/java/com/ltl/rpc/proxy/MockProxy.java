package com.ltl.rpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class MockProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        log.info("MockProxy invoke method: {}", method.getName());

        return this.getDefaultValue(returnType);
    }


    private Object getDefaultValue(Class<?> returnType) {
        if (returnType == int.class || returnType == Integer.class) {
            return 0;
        }else if(returnType == long.class || returnType == Long.class){
            return 0L;
        } else if (returnType == boolean.class || returnType == Boolean.class) {
            return true;
        } else if (returnType == String.class) {
            return "str";
        }

        return null;
    }

}
