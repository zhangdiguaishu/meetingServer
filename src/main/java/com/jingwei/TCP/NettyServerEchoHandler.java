package com.jingwei.TCP;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.*;

@ChannelHandler.Sharable
public class NettyServerEchoHandler extends ChannelInboundHandlerAdapter {
    //我们希望在此处理器之中，首先读到的16Byte作为编号存储
    //剩下来的部分写入到文件之中

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connected");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        ByteBuf in = (ByteBuf) msg;

        /*File file = new File("D:\\t\\a\\header_v1");
        if(!file.exists())
            file.createNewFile();
        FileOutputStream fop = new FileOutputStream(file);

        byte[] b = new byte[44];
        in.readBytes(b);
        fop.write(b);
        fop.flush();
        fop.close();*/

        int i = in.readInt();
        System.out.println(i);
        ByteBuf b;
        b = in.readBytes(in.readableBytes());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx){
        System.out.println("channelUnregistered");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    /*@Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }*/

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
