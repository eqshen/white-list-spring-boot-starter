package com.eqshen.demo.controller;

/**
 * @author eqshen
 * @description
 * @date 2021/4/13
 */
public class UserInfo {

    private String userId;

    private String name;

    private Integer age;

    private String address;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
