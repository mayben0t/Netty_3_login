package com.example.netty.client;

import com.example.netty.client.handler.LoginHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import static com.example.netty.constant.Constant.port;
import static com.example.netty.constant.Constant.host;

public class ClientBootStrap {

    public static void main(String[] args) {
        NioEventLoopGroup g1 = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(g1)
                 .channel(NioSocketChannel.class)
                 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                 .option(ChannelOption.SO_KEEPALIVE,true)
                 .option(ChannelOption.TCP_NODELAY,true)
                 .handler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     protected void initChannel(SocketChannel nioSocketChannel) throws Exception {
                         nioSocketChannel.pipeline().addLast(new LoginHandler());
                     }
                 });
        bootstrap.connect(host,port);
    }
}
