package com.hc.bootshiro.dao.test;

import org.apache.ibatis.annotations.Select;

/**
 * @Auther: 胡丛
 * @Date: 2018/9/21 09:58
 * @Description:
 */
public interface TestMapper {


    @Select("select test from test where id = 1")
    String select();
}
