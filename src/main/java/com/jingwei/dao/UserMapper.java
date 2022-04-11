package com.jingwei.dao;

import com.jingwei.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper//告诉spring这是一个mybatis的mapper
@Repository//将mapper交给spring管理
public interface UserMapper {
    public User queryUserByName(String userName);
    public String queryPasswordByName(String userName);
    public int registerNewUser(User user);
}
