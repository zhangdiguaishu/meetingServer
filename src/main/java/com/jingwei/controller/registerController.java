package com.jingwei.controller;

import com.alibaba.fastjson.JSONObject;
import com.jingwei.dao.UserMapper;
import com.jingwei.models.pojo.User;
import com.jingwei.service.impl.RegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class registerController {
    @Autowired
    private RegisterServiceImpl registerImpl;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/tryRegister")
    public String doInsert(@RequestBody String regJson){
        JSONObject jsonObject = JSONObject.parseObject(regJson);
        JSONObject retJsonObject = new JSONObject();

        String userName = jsonObject.getString("username");
        if(userMapper.queryUserByName(userName) != null){
            retJsonObject.put("state", "conflicted");
            return  retJsonObject.toJSONString();
        }
        String userPassword = jsonObject.getString("password");
        String userPhoneNumber = jsonObject.getString("phonenumber");
        String userEmailAddress = jsonObject.getString("email");

        User user = new User(userName, userPassword, userPhoneNumber, userEmailAddress);
        registerImpl.doRegister(user);

        return jsonObject.toJSONString();
    }
}
