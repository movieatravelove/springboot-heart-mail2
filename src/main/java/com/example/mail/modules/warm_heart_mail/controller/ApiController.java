package com.example.mail.modules.warm_heart_mail.controller;

import com.example.mail.entity.Result;
import com.example.mail.modules.warm_heart_mail.service.ApiService;
import com.example.mail.modules.warm_heart_mail.service.SendEmailService;
import com.example.mail.modules.warm_heart_mail.component.SaveEchartsComponent2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhang
 * @Date: 2019/3/1 14:00
 * @Description:
 */
@Api(tags = "heart_mail", description = "爱心邮件")
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;
    @Autowired
    private SaveEchartsComponent2 saveEchartsComponent2;
    @Autowired
    private SendEmailService sendEmailService;

    /**
     * @param cityname
     * @throws Exception
     */
    @GetMapping(value = "/getWeather")
    public Result getWeather(String cityname) {
        return apiService.getWeather(cityname);
    }


//    @GetMapping(value = "/test")
//    public String test() throws InterruptedException {
//        return saveEchartsComponent2.saveSurfModelUrlToImg("双子座");
//    }

    @ApiOperation("测试发送邮件")
    @GetMapping(value = "/sendEmailTest")
    public boolean send(@RequestParam(required = true,defaultValue = "892670873@qq.com") String eamil,
                     @RequestParam(required = true,defaultValue = "大哥") String username,
                     @RequestParam(required = true,defaultValue = "19950515") String meetDateStr,
                     @RequestParam(required = true,defaultValue = "西安") String cityname,
                     @RequestParam(required = true,defaultValue = "双子座") String consName) {
        return sendEmailService.sendHeartEmail(eamil, username, meetDateStr,
                cityname, consName);
    }

}
