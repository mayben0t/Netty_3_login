package com.example.netty.Server;

import com.example.netty.deSerializerUtil.DecodeUtil;
import com.example.netty.deSerializerUtil.impl.FastJsonDecodeUtil;
import com.example.netty.model.LoginModel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Objects;

@Slf4j
public class ServerLoginHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf response = ByteBufAllocator.DEFAULT.buffer(10, 100);
            response.retain();
            DecodeUtil decodeUtil = new FastJsonDecodeUtil();
            //        super.channelRead(ctx, msg);
            ByteBuf byteBuf = (ByteBuf) msg;
            LoginModel decode = decodeUtil.decode(byteBuf, LoginModel.class);
            if (Objects.isNull(decode)) {
                response.writeBytes("false".getBytes(Charset.forName("utf-8")));
            }
            try {
                if (decode.getUserName().equals("admin") && decode.getPassword().equals("123456")) {
                    response.writeBytes("success".getBytes(Charset.forName("utf-8")));
                }
            } catch (Exception e) {
                response.writeBytes("false".getBytes(Charset.forName("utf-8")));
            }
            log.debug(">>>>>>>返回给客户端的结果:[{}]",response.toString(Charset.forName("utf-8")));
            ctx.writeAndFlush(response);
            response.release();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
