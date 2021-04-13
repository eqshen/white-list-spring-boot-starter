package com.eqshen.demo.whitelist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author eqshen
 * @description
 * @date 2021/4/13
 */
@ConfigurationProperties(prefix = "eqshen.whitelist")
public class WhiteListProperties {

    private String userIds;

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getUserIds() {
        return userIds;
    }

}
