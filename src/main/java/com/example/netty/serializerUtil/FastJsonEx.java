package com.example.netty.serializerUtil;

import com.alibaba.fastjson.JSON;

public class FastJsonEx extends SerializerTemplate {

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
        return bytes;
    }
}
