package com.yang.yangrpc.config;

import com.yang.yangrpc.serializer.Serializer;
import com.yang.yangrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * Created by CaoYang in 2025-01-13
 * RPC框架配置
 */

@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "yang-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8081;

    /**
     * 模拟调用开关
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置
     */
    private RegisterConfig registerConfig = new RegisterConfig();
}
