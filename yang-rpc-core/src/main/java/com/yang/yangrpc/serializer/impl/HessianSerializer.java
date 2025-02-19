package com.yang.yangrpc.serializer.impl;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.yang.yangrpc.serializer.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by CaoYang in 2025-01-16
 * HessianSerializer 序列化器
 */
public class HessianSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(bos);
        ho.writeObject(object);
        return bos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        HessianInput hi = new HessianInput(bis);
        return (T) hi.readObject(type);
    }
}
