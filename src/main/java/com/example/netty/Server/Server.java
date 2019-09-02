package com.example.netty.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Server {


    public static void main(String[] args) {
        NioEventLoopGroup g1 = new NioEventLoopGroup();
        NioEventLoopGroup g2 = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(g1,g2)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_BACKLOG,1)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerLoginHandler());
                        }
                    });

        serverBootstrap.bind(7777).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("7777绑定成功");
            }else {
                System.out.println("服务器启动失败");
            }
        });
    }
}
