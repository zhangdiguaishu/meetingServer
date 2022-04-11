package com.jingwei.TCP;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class NettyServerRecord {
    //BOSS
    private EventLoopGroup boss = new NioEventLoopGroup();
    //WORKER
    private EventLoopGroup worker = new NioEventLoopGroup();

    @Value("${netty.port}")
    private Integer port;//configure netty port in application.yaml

    @Async
    public void start() throws  InterruptedException{
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                //set this.childiHandler
                .childHandler(new NettyServerRecordChannelInitializer());

        ChannelFuture future = bootstrap.bind().sync();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(future.isSuccess()){
                    System.out.println("Server starting successes.");
                }else{
                    System.out.println("Server starting fails.");
                }
            }
        });

        //等待boss channel的关闭
        future.channel().closeFuture().sync();
    }
}
