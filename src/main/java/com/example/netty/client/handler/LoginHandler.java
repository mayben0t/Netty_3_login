package com.example.netty.client.handler;

import com.example.netty.model.LoginModel;
import com.example.netty.serializerUtil.FastJsonEx;
import com.example.netty.serializerUtil.SerializerTemplate;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
//import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.Scanner;

import static com.example.netty.constant.Constant.COMMAND;
import static com.example.netty.constant.Constant.MAGIC_NUM;
import static com.example.netty.constant.Constant.PROTOCOL_VERSION;

@Slf4j
public class LoginHandler extends ChannelInboundHandlerAdapter {
    SerializerTemplate serializerTemplate = new FastJsonEx();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
//        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("输入用户名:");
            String userName = sc.nextLine();
            System.out.println("输入密码");
            String password = sc.nextLine();
            LoginModel loginModel = new LoginModel(userName, password);
            byte[] request = serializerTemplate.SerializeContent(loginModel);
            int length = serializerTemplate.getContentBytesLength();

            setProperties(request,length,buffer);
            ctx.writeAndFlush(buffer);
//        }
    }

    private ByteBuf setProperties(byte[] content,int length,ByteBuf byteBuf){
        byteBuf.writeInt(MAGIC_NUM);
        byteBuf.writeByte(PROTOCOL_VERSION);
        byteBuf.writeByte(serializerTemplate.getSerializerVersion());
        byteBuf.writeByte(COMMAND);
        byteBuf.writeInt(length);
        byteBuf.writeBytes(content);
        return byteBuf;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        String response = buffer.toString(Charset.forName("utf-8"));
        if(StringUtils.isNotEmpty(response) && response.equalsIgnoreCase("success")){
            System.out.println("恭喜你 登录成功");
        }else {
            System.out.println("登录失败，请检查用户名密码");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      log.error(">>>>>>>>>客户端出错",cause);
    }
}
