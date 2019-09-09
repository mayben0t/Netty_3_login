package com.example.netty.server;

import com.example.netty.protocol.Packet;
import com.example.netty.protocol.PacketCodeC;
import com.example.netty.protocol.request.LoginRequestPacket;
import com.example.netty.protocol.request.MessageRequestPacket;
import com.example.netty.protocol.response.LoginResponsePacket;
import com.example.netty.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        if(packet instanceof LoginRequestPacket){
            log.info("收到客户端登陆请求：....");
            //登陆流程
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginRequestPacket.setVersion(packet.getVersion());
            if(valid(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
                log.info("登陆成功!");
            }else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                log.info("登陆失败!");
            }
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }else if(packet instanceof MessageRequestPacket){
            //客户端发来消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            log.info("收到客户端消息："+messageRequestPacket.getMessage());
            messageResponsePacket.setMessage("服务端回复【"+messageRequestPacket.getMessage()+"】");
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        return true;
    }
}
