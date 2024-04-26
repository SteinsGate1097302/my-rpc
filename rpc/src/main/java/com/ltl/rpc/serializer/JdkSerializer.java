package com.ltl.rpc.serializer;

import java.io.*;

public class JdkSerializer implements Serializer {

    /**
     * 将对象序列化为字节数组
     * @param object 要序列化的对象
     * @param <T> 对象的类型
     * @return 序列化后的字节数组
     */
    public <T> byte[] serialize(T object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从字节数组中反序列化对象
     * @param bytes 序列化的字节数组
     * @param clazz 对象的Class类型
     * @param <T> 对象的类型
     * @return 反序列化得到的对象
     */
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return clazz.cast(in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
