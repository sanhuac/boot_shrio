package com.hc.bootshiro.service.impl;

import com.hc.bootshiro.dao.test.TestMapper;
import com.hc.bootshiro.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 胡丛
 * @Date: 2018/9/21 09:57
 * @Description:
 */
@Service("testService")
public class TestServiceImpl implements TestService{


    @Autowired
    private TestMapper testDao;

    public String test(){
        String select = testDao.select();
        return select;
    }
}
