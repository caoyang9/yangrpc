package com.yang.yangrpc.server;

/**
 * Created by CaoYang in 2025-01-10
 * Http服务器接口
 */
public interface HttpServer {

    /**
     * 启动服务器
     * @param port
     */
    void doStart(int port);
}
