package com.jingwei.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jingwei.dao.UserMapper;
import com.jingwei.models.ResponseResult;
import com.jingwei.models.pojo.User;
//import com.jingwei.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {
    @Autowired
    private UserMapper userMapper;

    /*@Autowired
    private LoginServiceImpl loginImpl;

    @PostMapping("/user/login")
    public String login(@RequestBody String userInfoJson){
        JSONObject jsonObject = JSONObject.parseObject(userInfoJson);

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        User user = new User(username, password, "", "");
        ResponseResult responseResult = loginImpl.tryLogin(user);

        return JSON.toJSONString(responseResult);
        //依据返回值来进行处理
    }*/

    @PostMapping("/tryLogin")
    public String tryLogin(@RequestBody String userInfoJson){
        JSONObject jsonObject = JSONObject.parseObject(userInfoJson);
        ResponseResult responseResult = new ResponseResult();

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String truePassword = userMapper.queryPasswordByName(username);

        if(password.contentEquals(truePassword)){
            responseResult.setStatus(200);
            responseResult.setMessage("succ");
        }else{
            responseResult.setStatus(301);
            responseResult.setMessage("fail");
        }

        return JSON.toJSONString(responseResult);
    }
}
