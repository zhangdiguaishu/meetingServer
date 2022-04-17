package com.jingwei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jingwei.dao.UserMapper;
import com.jingwei.models.ResponseResult;
import com.jingwei.models.pojo.User;
import com.jingwei.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginServiceImpl loginImpl;

    @PostMapping("/user/login")
    public String login(@RequestBody String userInfoJson){
        JSONObject jsonObject = JSONObject.parseObject(userInfoJson);
        JSONObject retJsonObject = new JSONObject();

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        //String truePassword = userMapper.queryPasswordByName(username);

        User user = new User(username, password, "", "");
        ResponseResult responseResult = loginImpl.tryLogin(user);

        return JSON.toJSONString(responseResult);
        //依据返回值来进行处理
    }


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
