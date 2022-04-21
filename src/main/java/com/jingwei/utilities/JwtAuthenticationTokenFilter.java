/*
package com.jingwei.utilities;

import com.jingwei.dao.UserMapper;
import com.jingwei.models.UserLoginInfo;
import com.jingwei.models.pojo.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Objects;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if(StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String username;
        try{
            Claims claims = JWTUtil.parseJWT(token);
            username = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            throw new RemoteException("token异常");
        }
        //解析用户信息
        User user = userMapper.queryUserByName(username);
        if(Objects.isNull(user)){
            throw new RuntimeException("不存在的用户");
        }
        UserLoginInfo userLoginInfo = new UserLoginInfo(user);
        //存入SecurityContextHolder
        //TODO 获取权限信息封装
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userLoginInfo, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(request, response);
    }
}
*/
