package com.yang.yangrpc.config;

import lombok.Data;

/**
 * Created by CaoYang in 2025-02-11
 * RPC注册中心配置信息
 */

@Data
public class RegisterConfig {
    /**
     * 注册中心类别
     */
    private String registry = "etcd";

    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2380";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;
}

