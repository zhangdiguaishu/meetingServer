package com.jingwei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jingwei.dao.MeetingMapper;
import com.jingwei.pojo.Meeting;
import com.jingwei.utility.AudioProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class recordsCtl {
    @Autowired
    private MeetingMapper meetingMapper;

    @PostMapping("newMeeting")
    public String newMeeting(@RequestBody String meetingInfo){
        JSONObject jsonObject = JSONObject.parseObject(meetingInfo);
        JSONObject retJsonObject = new JSONObject();

        Meeting meeting = new Meeting(-1,
                jsonObject.getString("topic"),
                jsonObject.getString("date"),
                jsonObject.getString("list"),
                jsonObject.getString("brief"),
                jsonObject.getString("remark"),
                jsonObject.getString("username"),
                ""
        );//在插入会议记录时不需要设置meetingIndex，location应该需要重新设置

        if(meetingMapper.insertNewMeetingRecord(meeting) == 1){
            retJsonObject.put("state", "succeed");
            retJsonObject.put("meetingIndex", meeting.getIndex());
        }else{
            retJsonObject.put("state", "failed");
        }

        return retJsonObject.toJSONString();
    }
    @GetMapping("startProcess/{meetingIndex}")
    public void startProcess(@PathVariable("meetingIndex") String meetingIndex){
        AudioProcessor audioProcessor = new AudioProcessor(meetingIndex);
        audioProcessor.run();
    }

    @GetMapping("getRecordsByName/{userName}")
    public String getMeetingRecordsByUsername(@PathVariable("userName") String userName){
        List<Meeting> lMeeting = meetingMapper.queryMeetingRecordsByUsername(userName);

        JSONArray jsonArray = new JSONArray();
        for(Meeting meeting : lMeeting){
            JSONObject t = (JSONObject) JSONObject.toJSON(meeting);
            jsonArray.add(t);
        }

        return jsonArray.toJSONString();
    }
    @GetMapping("getRecordByIndex/{index}")
    public String getMeetingRecordByIndex(@PathVariable("index") int index){
        Meeting meeting = meetingMapper.queryMeetingRecordByIndex(index);
        return JSON.toJSONString(meeting);
    }
}
