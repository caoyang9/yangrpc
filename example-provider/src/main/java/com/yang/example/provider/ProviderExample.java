package com.yang.example.provider;

import com.yang.example.common.service.UserService;
import com.yang.yangrpc.RPCApplication;
import com.yang.yangrpc.config.RpcConfig;
import com.yang.yangrpc.registry.LocalRegistry;
import com.yang.yangrpc.server.VertxHttpServer;

/**
 * Created by CaoYang in 2025-01-13
 * 简易服务提供者示例
 */
public class ProviderExample {

    public static void main(String[] args) {

        // Rpc框架初始化
        RPCApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动web服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RPCApplication.getRpcConfig().getServerPort());
    }
}
