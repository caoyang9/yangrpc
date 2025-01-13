package com.yang.example.consumer;

import com.yang.yangrpc.config.RpcConfig;
import com.yang.yangrpc.utils.ConfigUtils;

/**
 * Created by CaoYang in 2025-01-13
 * 简易服务消费者示例
 */

public class ConsumerExample {

    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
