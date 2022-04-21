package com.jingwei.service.impl;

import com.jingwei.dao.UserMapper;
import com.jingwei.models.ResponseResult;
import com.jingwei.models.pojo.User;
import com.jingwei.service.itfc.RegisterServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RegisterServiceImpl implements RegisterServiceInterface {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult tryRegister(User user) {
        ResponseResult responseResult = new ResponseResult();
        if(! Objects.isNull(userMapper.queryUserByName(user.getName()))){
            responseResult.setStatus(302);
            responseResult.setMessage("username confilcts!");
        }else{
            if(userMapper.registerNewUser(user) == 1){
                responseResult.setStatus(200);
                responseResult.setMessage("register succ");
            }else{
                responseResult.setStatus(303);
                responseResult.setMessage("database goes wrong!");
            }
        }

        return responseResult;
    }
}
