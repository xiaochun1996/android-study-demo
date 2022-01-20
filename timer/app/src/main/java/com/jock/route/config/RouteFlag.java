package com.jock.route.config;

/**
 * 为目标页扩展属性
 */
public interface RouteFlag {
    /**
     * 登陆
     */
    int FLAG_LOGIN = 0x01;
    /**
     * 实名认证
     */
    int FLAG_AUTHENTICATION = FLAG_LOGIN << 1;

}
