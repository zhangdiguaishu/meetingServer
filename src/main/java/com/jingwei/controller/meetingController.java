package com.jingwei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jingwei.dao.SeparatedAudioMapper;
import com.jingwei.models.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class meetingController {
    @Autowired
    private SeparatedAudioMapper separatedAudioMapper;

    @GetMapping("meeting/iscompleted/{meetingIndex}")
    public String isMeetingCompleted(@PathVariable("meetingIndex")String meetingIndex){
        ResponseResult responseResult = new ResponseResult();
        File completionFile = new File("D:\\_code\\project_java_meetingServer\\ideaProj\\processedRecords\\" + meetingIndex + "\\complete");
        if(completionFile.exists()){
            responseResult.setStatus(200);
        }else{
            responseResult.setStatus(304);
        }

        return JSON.toJSONString(responseResult);
    }

    @GetMapping("meeting/separatedaudionamelist/{meetingIndex}")
    public String querySeparatedAudioName(@PathVariable("meetingIndex") String meetingIndex){
        ResponseResult responseResult = new ResponseResult();
        int tindex = Integer.parseInt(meetingIndex);
        List<String> audioNameList = separatedAudioMapper.queryAudioNameByMeetingIndex(tindex);

        JSONObject jsonObject = new JSONObject();
        responseResult.setStatus(200);

        int cnt = 1;
        for(String audioname : audioNameList){
            jsonObject.put(String.valueOf(cnt), audioname);
            ++cnt;
        }

        return jsonObject.toJSONString();
    }
}
