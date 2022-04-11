/*
package com.jingwei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.util.concurrent.TimeUnit;

@RestController
public class tsltCtl {
    private static final String APP_ID = "23f236b6";
    private static final String SECRET_KEY = "fc72008f5a2b5b913e7770614c2ccd56";

    @PostMapping("/translate")
    public String translate(@RequestBody String request) throws UnsupportedEncodingException, InterruptedException {
        LfasrClient tsltClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);

        request = URLDecoder.decode(request, "utf-8");
        JSONObject jobj = JSON.parseObject(request);

        String rname = jobj.getString("source");
        String rpath = "/";
        if(rname == "http://114.212.83.227/wav_1.wav"){
            rpath = tsltCtl.class.getResource("").getPath()+ "/static/wav_1.wav";
        }else if(rname == "http://114.212.83.227/wav_2.wav"){
            rpath = tsltCtl.class.getResource("").getPath()+ "/static/wav_2.wav";
        }
        //创建任务
        Message task = tsltClient.upload(jobj.getString(rpath));
        String taskId = task.getData();
        System.out.println("转写任务 taskId：" + taskId);

        //查看进度
        int status = 0;
        while (status != 9) {
            Message message = tsltClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            if (object ==null) throw new LfasrException(message.toString());
            status = object.getInteger("status");
            System.out.println(message.getData());
            TimeUnit.SECONDS.sleep(2);
        }
        //获取结果
        Message result = tsltClient.getResult(taskId);
        System.out.println("转写结果: \n" + result.getData());

        return result.getData();
    }



}
*/
