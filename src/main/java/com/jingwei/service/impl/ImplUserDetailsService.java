/*
package com.jingwei.service.impl;

import com.jingwei.dao.UserMapper;
import com.jingwei.models.UserLoginInfo;
import com.jingwei.models.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ImplUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户讯息
        User user = userMapper.queryUserByName(username);
        if(Objects.isNull(user)){
            throw new RuntimeException("user doesn't exist or password is wrong!");
        }

        //封装成userDetails返回
        return new UserLoginInfo(user);
    }
}
*/
