package com.hc.bootshiro.controller.test;

import com.hc.bootshiro.controller.BaseController;
import com.hc.bootshiro.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 胡丛
 * @Date: 2018/9/21 09:54
 * @Description:
 */
@RestController
@RequestMapping("test")
public class TestController extends BaseController{

    @Autowired
    private TestService testService;

    @RequestMapping("test1")
    public String test(){
        String test = testService.test();
        return test;
    }
}
