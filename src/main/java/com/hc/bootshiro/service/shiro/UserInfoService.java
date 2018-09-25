package com.hc.bootshiro.service.shiro;

import com.hc.bootshiro.bean.shiro.UserInfo;

/**
 * @Auther: 胡丛
 * @Date: 2018/9/21 10:38
 * @Description:
 */
public interface UserInfoService {

    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
}
