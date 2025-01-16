package com.yang.example.consumer;

import com.yang.example.common.model.User;
import com.yang.example.common.service.UserService;
import com.yang.yangrpc.proxy.ServiceProxyFactory;

import java.time.LocalDateTime;

/**
 * Created by CaoYang in 2025-01-10
 * 简单服务消费者示例
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        // 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yang");

        // 调用
        User newUser = userService.getUser(user);

        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        long number = userService.getNumber();
        System.out.println(number);
    }
}
