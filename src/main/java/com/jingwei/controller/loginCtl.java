package com.jingwei.controller;

import com.alibaba.fastjson.JSONObject;
import com.jingwei.dao.UserMapper;
import com.jingwei.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginCtl {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/tryLogin")
    public String tryLogin(@RequestBody String userInfoJson){
        JSONObject jsonObject = JSONObject.parseObject(userInfoJson);
        JSONObject retJsonObject = new JSONObject();

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String truePassword = userMapper.queryPasswordByName(username);

        if(password.contentEquals(truePassword))
            retJsonObject.put("state", "succeed");
        else
            retJsonObject.put("state", "failed");

        return retJsonObject.toJSONString();
    }
}
