package com.yang.yangrpc.config;

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
    private Integer serverPort = 8080;
}
