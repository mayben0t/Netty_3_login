package com.example.netty.serializerUtil;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastJsonEx extends SerializerTemplate {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void setSerializerVersion() {
        this.SerializerVersion = 1;
    }

    @Override
    public int getContentBytesLength() {
        return SerializeContent(obj).length;
    }

    @Override
    public byte[] SerializeContent(Object obj) {
        this.obj = obj;
        byte[] bytes = JSON.toJSONBytes(obj);
        logger.info(">>>>>>>>发送请求的长度:[{}]",bytes.length);
        return bytes;
    }
}
