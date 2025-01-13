package com.yang.example.provider;

import com.yang.example.common.service.UserService;
import com.yang.yangrpc.registry.LocalRegistry;
import com.yang.yangrpc.server.VertxHttpServer;

/**
 * Created by CaoYang in 2025-01-10
 * 简单服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动web服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
