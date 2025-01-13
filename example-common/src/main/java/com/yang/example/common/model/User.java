package com.yang.example.common.model;


import java.io.Serializable;

/**
 * Created by CaoYang in 2025-01-10
 * 用户实体
 */
public class User implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
