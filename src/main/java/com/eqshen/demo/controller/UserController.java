package com.eqshen.demo.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.eqshen.demo.whitelist.JoinPointProcessor;
import com.eqshen.demo.whitelist.annotation.ApplyWhiteList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eqshen
 * @description
 * @date 2021/4/13
 */
@RestController
public class UserController {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String defaultResult = "{\"msg\":\"拦截非法用户\",\"code\":10001}";

    @GetMapping("/user")
    @ApplyWhiteList(key = "userId",returnResult = defaultResult)
    public String getUser(@RequestParam("userId") String userId){
        log.info("查询用户信息：{}",userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setName("张三");
        userInfo.setAge(20 + (int)Math.random()*10);
        userInfo.setAddress("上海市黄浦区abcd");
        return JSONUtil.toJsonStr(userInfo);
    }

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.set("code",10001);
        json.set("msg","拦截非法用户");
        System.out.println(json.toString());
    }
}
