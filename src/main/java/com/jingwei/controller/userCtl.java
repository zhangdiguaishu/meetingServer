package com.jingwei.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//控制登录
/*接收传过来的用户名，检查保存用户的json文件之中是否有对应的用户名
* 如果有则检查密码，正确即跳转
* 如果没有则提示注册
*/
@RestController
public class userCtl {
    public static String user;

    @PostMapping("/login")
    public String tryLogin(@RequestBody String loginInfoStr){
       return "None";
    }
}
