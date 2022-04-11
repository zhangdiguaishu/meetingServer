package com.jingwei.TCP;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

@Component
public class NettyServerEcho {
    @Value("${netty.port}")
    private int port;

    /*@Override
    public void run(){
        System.out.println("run()");
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        this.start(inetSocketAddress);
    }*/

    @Async
    public void start(){
        final NettyServerEchoHandler serverHandler = new NettyServerEchoHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        //事务处理
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)//使用Nio来接受和处理新的连接
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                            throws Exception{
                                ch.pipeline().addLast(serverHandler);
                                //用来给子进程（worker）的pipeline添加handler
                            }
                    });
            System.out.println("nettyServerEcho.start()");
            ChannelFuture f = b.bind().sync();

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
