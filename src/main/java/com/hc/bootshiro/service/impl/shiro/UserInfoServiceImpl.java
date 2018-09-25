package com.hc.bootshiro.service.impl.shiro;

import com.hc.bootshiro.bean.shiro.UserInfo;
import com.hc.bootshiro.dao.shiro.UserInfoDao;
import com.hc.bootshiro.service.shiro.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: 胡丛
 * @Date: 2018/9/21 10:43
 * @Description:
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{

    @Resource
    private UserInfoDao userInfoDao;


    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}
