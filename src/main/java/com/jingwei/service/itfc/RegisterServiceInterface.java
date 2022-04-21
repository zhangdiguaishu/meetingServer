package com.jingwei.service.itfc;

import com.jingwei.models.ResponseResult;
import com.jingwei.models.pojo.User;

public interface RegisterServiceInterface {
    public ResponseResult tryRegister(User user);
}
