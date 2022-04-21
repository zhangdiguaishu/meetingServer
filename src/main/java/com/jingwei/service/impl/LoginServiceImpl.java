/*
package com.jingwei.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jingwei.models.ResponseResult;
import com.jingwei.models.UserLoginInfo;
import com.jingwei.models.pojo.User;
import com.jingwei.service.itfc.LoginServiceInterface;
import com.jingwei.utilities.JWTUtil;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginServiceInterface {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult tryLogin(User user) {
        //获取AuthenticationManager进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);//这一行会使用UserDetailsServices里面的内容进行校验

        ResponseResult responseResult = new ResponseResult();
        if(Objects.isNull(authentication)) {
            responseResult.setStatus(100);
            responseResult.setMessage("账号或密码错误");
            throw new RuntimeException("账号或密码错误");
            //return responseResult;
        }else{
            responseResult.setStatus(200);
            responseResult.setMessage("登录成功");
        }

        UserLoginInfo userLoginInfo = (UserLoginInfo) authentication.getPrincipal();
        String jwt = JWTUtil.createJWT(userLoginInfo.getUsername());

        JSONObject tokenJsonObject = new JSONObject();
        tokenJsonObject.put("token", jwt);
        responseResult.setData(tokenJsonObject.toJSONString());

        return responseResult;
    }
}
*/
