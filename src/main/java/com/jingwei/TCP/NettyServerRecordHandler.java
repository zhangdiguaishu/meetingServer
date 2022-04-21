package com.jingwei.TCP;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NettyServerRecordHandler extends ChannelInboundHandlerAdapter {
    static Map<Integer, Integer> indexMap;

    static{
        indexMap = new HashMap<>();
    }

    boolean initialized = false;
    int meetingIndex;
    int curIndex;
    File curFile;
    FileOutputStream curFop;

    boolean isTerminated = false;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("a new channel established!" + "The opposite is: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf in = (ByteBuf) msg;
        System.out.println(((ByteBuf) msg).readableBytes());

        if(!initialized){
            initialized = true;

            meetingIndex = in.readInt();
            if(meetingIndex == 0){
                System.out.println("************************************meetingTerminate********************");
                isTerminated = true;
                meetingIndex = in.readInt();
                String terminateFilePath =  "receivedRecords" + "/" + meetingIndex + "/" + "done";
                System.out.println(terminateFilePath);
                File terminateFile = new File(terminateFilePath);
                terminateFile.createNewFile();
                return;
            }

            if(indexMap.containsKey(meetingIndex)){
                curIndex = indexMap.get(meetingIndex) + 1;
                indexMap.put(meetingIndex, curIndex);
            }else{
                curIndex = 1;
                indexMap.put(meetingIndex, 1);
                File dir = new File("receivedRecords" + "/" + meetingIndex);
                dir.mkdir();
            }

            curFile = new File("receivedRecords" + "/" + meetingIndex + "/" + curIndex + ".wav");
            curFile.createNewFile();
            curFop = new FileOutputStream(curFile);

            byte[] b = new byte[44];
            in.readBytes(b);
            curFop.write(b);
            curFop.flush();
        }

        byte[] t = new byte[in.readableBytes()];
        in.readBytes(t);
        in.release();
        curFop.write(t);
        curFop.flush();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws IOException {
        System.out.println("channelUnregistered");
        if(! isTerminated) curFop.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

}
