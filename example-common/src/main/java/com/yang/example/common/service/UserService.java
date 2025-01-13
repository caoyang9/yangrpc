package com.yang.example.common.service;

import com.yang.example.common.model.User;

/**
 * Created by CaoYang in 2025-01-10
 * 用户服务
 */
public interface UserService {

    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);
}
