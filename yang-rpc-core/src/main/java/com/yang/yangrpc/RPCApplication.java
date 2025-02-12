package com.yang.yangrpc;

import com.yang.yangrpc.config.RegisterConfig;
import com.yang.yangrpc.config.RpcConfig;
import com.yang.yangrpc.constant.RpcConstant;
import com.yang.yangrpc.registry.Registry;
import com.yang.yangrpc.registry.RegistryFactory;
import com.yang.yangrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by CaoYang in 2025-01-13
 * RPC框架应用
 * 存放了项目全局使用的变量，使用双检锁单例模式实现
 */

@Slf4j
public class RPCApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     * RPC框架初始化，支持传入自定义配置
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig){
        rpcConfig = newRpcConfig;
        log.info("RPC init, config = {}", newRpcConfig.toString());

        // 注册中心初始化
        RegisterConfig registerConfig = rpcConfig.getRegisterConfig();
        Registry registry = RegistryFactory.getInstance(registerConfig.getRegistry());
        registry.init(registerConfig);
        log.info("registry init, config = {}", registerConfig);
    }

    /**
     * 初始化
     */
    public static void init(){
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e){
            // 配置加载失败，使用默认配置
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     * 获取配置时才会调用init，懒加载配置
     * @return
     */
    public static RpcConfig getRpcConfig(){
        if(rpcConfig == null){
            synchronized(RPCApplication.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
