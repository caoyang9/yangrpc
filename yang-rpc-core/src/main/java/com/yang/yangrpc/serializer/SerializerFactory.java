package com.yang.yangrpc.serializer;

import com.yang.yangrpc.serializer.impl.JdkSerializer;
import com.yang.yangrpc.spi.SpiLoader;

/**
 * Created by CaoYang in 2025-01-16
 * 序列化器工厂
 */
public class SerializerFactory {

    static {
        System.out.println("==============");
        SpiLoader.load(Serializer.class);
    }

//    /**
//     * 序列化器映射，用于实现单例
//     */
//    private static final Map<String, Serializer> KEY_SERIALIZER_MAP = new HashMap<String, Serializer>(){
//        {
//            put(SerializerKeys.JDK, new JdkSerializer());
//            put(SerializerKeys.JSON, new JsonSerializer());
//            put(SerializerKeys.KRYO, new KryoSerializer());
//            put(SerializerKeys.HESSIAN, new HessianSerializer());
//        }
//    };

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取序列化器实例
     * @param key
     * @return
     */
    public static Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
