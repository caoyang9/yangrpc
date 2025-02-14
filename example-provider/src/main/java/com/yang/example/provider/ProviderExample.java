package com.yang.example.provider;

import com.yang.example.common.service.UserService;
import com.yang.yangrpc.RPCApplication;
import com.yang.yangrpc.config.RegisterConfig;
import com.yang.yangrpc.config.RpcConfig;
import com.yang.yangrpc.model.ServiceMetaInfo;
import com.yang.yangrpc.registry.LocalRegistry;
import com.yang.yangrpc.registry.Registry;
import com.yang.yangrpc.registry.RegistryFactory;
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
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RPCApplication.getRpcConfig();
        RegisterConfig registerConfig = rpcConfig.getRegisterConfig();
        Registry registry = RegistryFactory.getInstance(registerConfig.getRegistry());
        ServiceMetaInfo metaInfo = new ServiceMetaInfo();
        metaInfo.setServiceName(serviceName);
        metaInfo.setServiceHost(rpcConfig.getServerHost());
        metaInfo.setServicePort(rpcConfig.getServerPort());

        try {
            registry.register(metaInfo);
        }catch (Exception e){
            throw new RuntimeException("服务注册失败", e);
        }

        // 启动web服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RPCApplication.getRpcConfig().getServerPort());
    }
}
