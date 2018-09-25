package com.hc.bootshiro.dao.shiro;

import com.hc.bootshiro.bean.shiro.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @Auther: 胡丛
 * @Date: 2018/9/21 10:44
 * @Description:
 */
public interface UserInfoDao extends CrudRepository<UserInfo,Long> {

    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);
}
