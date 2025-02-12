package com.yang.yangrpc.registry;

import cn.hutool.json.JSONUtil;
import com.yang.yangrpc.config.RegisterConfig;
import com.yang.yangrpc.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by CaoYang in 2025-02-11
 * Etcd注册中心
 */

@Slf4j
public class EtcdRegistry implements Registry{

    private Client client;

    private KV kvClient;

    /**
     * 根节点
     */
    private static final String ETCD_ROOT_PATH = "/rpc/";

    /**
     * 初始化
     * @param registerConfig
     */
    @Override
    public void init(RegisterConfig registerConfig) {
        client = Client
                .builder()
                .endpoints(registerConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registerConfig.getTimeout()))
                .build();
        kvClient = client.getKVClient();
    }

    @Override
    public void register(ServiceMetaInfo metaInfo) throws Exception {
        // 创建Lease和KV客户端
        Lease leaseClient = client.getLeaseClient();

        // 创建一个30秒的租约
        long leaseId = leaseClient.grant(300).get().getID();

        // 设置要存储的键值对
        String registerKey = ETCD_ROOT_PATH + metaInfo.getServiceNodeKey();
        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(metaInfo), StandardCharsets.UTF_8);

        // 将键值对和租约关联起来，并设置过期时间
        PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
        kvClient.put(key, value, putOption).get();
    }

    @SneakyThrows
    @Override
    public void unRegister(ServiceMetaInfo metaInfo) {
        System.out.println(ETCD_ROOT_PATH + metaInfo.getServiceNodeKey());
        kvClient.delete(
                ByteSequence.from(
                        ETCD_ROOT_PATH + metaInfo.getServiceNodeKey(), StandardCharsets.UTF_8
                )).get();
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        // 前缀搜索，结尾一定要加"/"
        String searchPrefix = ETCD_ROOT_PATH + serviceKey + '/';
        log.info(searchPrefix);

        try{
            // 前缀查询
            GetOption getOption = GetOption.builder().isPrefix(true).build();
            List<KeyValue> keyValues = kvClient.get(ByteSequence.from(searchPrefix, StandardCharsets.UTF_8), getOption)
                    .get().getKvs();

            // 解析服务信息
            return keyValues.stream()
                    .map(keyValue -> {
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        return JSONUtil.toBean(value, ServiceMetaInfo.class);
                    }).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    @Override
    public void destroy() {
        System.out.println("当前节点下线");
        // 释放资源
        if(kvClient != null){
            kvClient.close();
        }
        if(client != null){
            client.close();
        }
    }
}
