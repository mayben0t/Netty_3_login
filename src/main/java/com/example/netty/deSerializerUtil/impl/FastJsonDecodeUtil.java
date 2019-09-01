package com.example.netty.deSerializerUtil.impl;

import com.example.netty.deSerializerUtil.DecodeUtil;
import io.netty.buffer.ByteBuf;

import java.util.Objects;

public class FastJsonDecodeUtil extends DecodeUtil {
    @Override
    public <LoginModel> LoginModel decode(ByteBuf byteBuf,Class clazz) {
        if(Objects.isNull(byteBuf))
            return null;
        byteBuf.skipBytes(4); //skip magic_num
        byteBuf.skipBytes(1); //skip version
        byteBuf.skipBytes(1); //skip seriaLize_version
    }
}
