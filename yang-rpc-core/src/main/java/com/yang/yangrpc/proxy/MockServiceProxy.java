package com.yang.yangrpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by CaoYang in 2025-01-16
 * Mock服务代理（JDK动态代理）
 */

@Slf4j
public class MockServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        log.info("mock invoke {}", method.getName());
        return getDefaultObject(returnType);
    }

    /**
     * 生成指定类型的默认值对象
     * @param type
     * @return
     */
    private Object getDefaultObject(Class<?> type) {
        // 基本数据类型
        if(type.isPrimitive()){
            if(type == boolean.class){
                return false;
            } else if (type == short.class) {
                return (short) 0;
            } else if (type == int.class) {
                return 0;
            }else if (type == long.class){
                return 0L;
            }
        }
        // 引用数据类型
        return null;
    }
}
