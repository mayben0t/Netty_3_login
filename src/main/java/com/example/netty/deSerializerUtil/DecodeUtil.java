package com.example.netty.deSerializerUtil;

import io.netty.buffer.ByteBuf;

/**
 * 用于将ByteBuf里的二进制数据反序列化成对象
 */
public abstract class DecodeUtil {

    public abstract <T> T decode(ByteBuf byteBuf,Class<T> clazz);
}
