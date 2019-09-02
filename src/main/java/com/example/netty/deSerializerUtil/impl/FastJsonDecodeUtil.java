package com.example.netty.deSerializerUtil.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.netty.deSerializerUtil.DecodeUtil;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class FastJsonDecodeUtil extends DecodeUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public <T> T decode(ByteBuf byteBuf,Class<T> clazz) {
        if(Objects.isNull(byteBuf)) {
            return null;
        }
        byteBuf.skipBytes(4); //skip magic_num
        byteBuf.skipBytes(1); //skip version
        byteBuf.skipBytes(1); //skip seriaLize_version
        byteBuf.skipBytes(1); //skip command
        int length = byteBuf.readInt();
        logger.info(">>>>>>length:[{}]",length);
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Object o = JSON.parseObject(bytes, clazz);
        return (T) o;
    }
}
