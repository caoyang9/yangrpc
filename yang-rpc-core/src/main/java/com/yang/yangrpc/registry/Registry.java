package com.yang.yangrpc.registry;

import com.yang.yangrpc.config.RegisterConfig;
import com.yang.yangrpc.model.ServiceMetaInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by CaoYang in 2025-02-11
 * 注册中心
 */

public interface Registry {

    /**
     * 初始化
     * @param registerConfig
     */
    void init(RegisterConfig registerConfig);

    /**
     * 注册服务（服务端）
     * @param metaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo metaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     * @param metaInfo
     */
    void unRegister(ServiceMetaInfo metaInfo) throws ExecutionException, InterruptedException;

    /**
     * 服务发现（消费端：获取某个服务的所有节点）
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测
     */
    void heartBeat();
}
