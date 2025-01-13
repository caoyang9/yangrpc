package com.yang.example.provider;

import com.yang.example.common.model.User;
import com.yang.example.common.service.UserService;

import java.time.LocalDateTime;

/**
 * Created by CaoYang in 2025-01-10
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println(LocalDateTime.now() + " 开始-执行业务方法");
        System.out.println("我的用户名：" + user.getName());
        System.out.println(LocalDateTime.now() + " 结束-执行业务方法");
        return user;
    }
}
