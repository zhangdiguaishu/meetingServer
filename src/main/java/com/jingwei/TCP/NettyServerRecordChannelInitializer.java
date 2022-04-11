package com.jingwei.TCP;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class NettyServerRecordChannelInitializer extends ChannelInitializer<SocketChannel> {
    //though there is nothing "concrete" in this class
    //but this class inherit ChannelInitializer<SocketChannel>
    //so there are something "concrete" in this class in practice

    //I think netty will call this method
    //so we create our customized version and override it
    @Override
    protected void initChannel(SocketChannel ch) throws Exception{
        //在此处给channel的pipeline里面添加处理器
        ch.pipeline().addLast(new NettyServerRecordHandler());
    }
}
