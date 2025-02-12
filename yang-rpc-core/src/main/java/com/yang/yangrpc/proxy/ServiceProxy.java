package com.yang.yangrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.yang.yangrpc.RPCApplication;
import com.yang.yangrpc.config.RpcConfig;
import com.yang.yangrpc.constant.RpcConstant;
import com.yang.yangrpc.model.RpcRequest;
import com.yang.yangrpc.model.RpcResponse;
import com.yang.yangrpc.model.ServiceMetaInfo;
import com.yang.yangrpc.registry.Registry;
import com.yang.yangrpc.registry.RegistryFactory;
import com.yang.yangrpc.serializer.Serializer;
import com.yang.yangrpc.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by CaoYang in 2025-01-10
 * JDK动态代理
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我被自动调用了 " + this);
        // 指定序列化器
//        Serializer serializer = new JdkSerializer();
        String serviceName = method.getDeclaringClass().getName();
        Serializer serializer = SerializerFactory.getInstance(RPCApplication.getRpcConfig().getSerializer());

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 从注册中心获取服务提供者的请求地址
            RpcConfig rpcConfig = RPCApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegisterConfig().getRegistry());

            ServiceMetaInfo metaInfo = new ServiceMetaInfo();
            metaInfo.setServiceName(serviceName);
            metaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);

            List<ServiceMetaInfo> serviceMetaInfosList = registry.serviceDiscovery(metaInfo.getServiceKey());

            if(CollUtil.isEmpty(serviceMetaInfosList)){
                throw new RuntimeException("暂无服务地址");
            }
            // TODO 负载均衡实现
            ServiceMetaInfo selectServiceMetaInfo = serviceMetaInfosList.get(0);

            // 发送请求
            // todo 注意，这里地址被硬编码了（需要使用注册中心和服务发现机制解决）
            System.out.println(LocalDateTime.now() + " 代理对象发起远程调用");
            try (HttpResponse httpResponse = HttpRequest.post(selectServiceMetaInfo.getServiceAddress())
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                System.out.println(rpcResponse.toString());
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
