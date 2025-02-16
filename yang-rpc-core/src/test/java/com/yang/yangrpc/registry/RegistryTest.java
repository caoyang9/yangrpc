package com.yang.yangrpc.registry;

import com.yang.yangrpc.config.RegisterConfig;
import com.yang.yangrpc.model.ServiceMetaInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by CaoYang in 2025-02-12
 * 注册中心单测
 */
public class RegistryTest {

    final Registry registry = new EtcdRegistry();

    @Before
    public void init(){
        RegisterConfig registerConfig = new RegisterConfig();
        registerConfig.setAddress("http://localhost:2379");
        registry.init(registerConfig);
    }

    @Test
    public void register() throws Exception{
        ServiceMetaInfo metaInfo = new ServiceMetaInfo();
        metaInfo.setServiceName("myService");
        metaInfo.setServiceVersion("1.0");
        metaInfo.setServiceHost("localhost");
        metaInfo.setServicePort(1234);
        registry.register(metaInfo);

        metaInfo = new ServiceMetaInfo();
        metaInfo.setServiceName("myService");
        metaInfo.setServiceVersion("1.0");
        metaInfo.setServiceHost("localhost");
        metaInfo.setServicePort(1235);
        registry.register(metaInfo);

        metaInfo = new ServiceMetaInfo();
        metaInfo.setServiceName("myService");
        metaInfo.setServiceVersion("2.0");
        metaInfo.setServiceHost("localhost");
        metaInfo.setServicePort(1234);
        registry.register(metaInfo);
    }

    @Test
    public void unRegister() throws ExecutionException, InterruptedException {
        ServiceMetaInfo metaInfo = new ServiceMetaInfo();
        metaInfo.setServiceName("myService");
        metaInfo.setServiceVersion("1.0");
        metaInfo.setServiceHost("localhost");
        metaInfo.setServicePort(1234);
        registry.unRegister(metaInfo);
    }

    @Test
    public void serviceDiscovery(){
        ServiceMetaInfo metaInfo = new ServiceMetaInfo();
        metaInfo.setServiceName("myService");
        metaInfo.setServiceVersion("1.0");
        String serviceKey = metaInfo.getServiceKey();
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceKey);
        System.out.println(serviceMetaInfoList.toString());
        Assert.assertNotNull(serviceMetaInfoList);
    }

    @Test
    public void heartBeat() throws Exception {
        register();
        // 阻塞1分钟
        Thread.sleep(60 * 1000L);
    }

}
