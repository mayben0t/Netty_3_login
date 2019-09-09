package com.example.netty.client;

import com.example.netty.protocol.Packet;
import com.example.netty.protocol.PacketCodeC;
import com.example.netty.protocol.request.LoginRequestPacket;
import com.example.netty.protocol.response.LoginResponsePacket;
import com.example.netty.protocol.response.MessageResponsePacket;
import com.example.netty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.UUID;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    {
        threadLocal.set(simpleDateFormat);
    }
    private String getDate(){
        LocalDate now = LocalDate.now();
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = now.atStartOfDay(zoneId).toInstant();
        java.util.Date nowTime = Date.from(instant);
        return threadLocal.get().format(nowTime);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端开始登陆");

        //创建登陆对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("mayben0t");
        loginRequestPacket.setPassword("123456");

        //编码
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        //写数据
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if(loginResponsePacket.isSuccess()){
                log.info("客户端登陆成功");
                LoginUtil.markAsLogin(ctx.channel());
            }else {
                log.info("客户端登陆失败，原因："+loginResponsePacket.getReason());
            }
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            log.info("收到服务端的消息:"+messageResponsePacket.getMessage());
        }
    }
}
