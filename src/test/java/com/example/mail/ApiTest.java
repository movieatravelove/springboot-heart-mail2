package com.example.mail;

import com.example.mail.utils.DateUtil;
import com.example.mail.modules.warm_heart_mail.service.impl.ApiServiceImpl;
import com.example.mail.modules.warm_heart_mail.component.SaveEchartsComponent2;
import com.example.mail.modules.warm_heart_mail.service.impl.SendEmailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author: zhang
 * @Date: 2019/3/1 14:02
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Autowired
    private ApiServiceImpl apiServiceImpl;
    @Autowired
    private SendEmailServiceImpl sendEmailService;
    @Autowired
    private SaveEchartsComponent2 saveEchartsComponent2;
    @Value("${filter.words}")
    private String filterWords;

    @Test
    public void send() {
        sendEmailService.sendHeartEmail("892670873@qq.com", "大哥", "20130901", "厦门", "天秤座");
    }

    @Test
    public void test() {
        System.out.println(filterWords);
    }


    @Test
    public void dateTest() {
        int hh = Integer.parseInt(DateUtil.getCurrDate("HH"));
        if (hh >= 0 && hh < 6) {
            System.out.println("凌晨");
        } else if (hh >= 6 && hh < 12) {
            System.out.println("早上");
        } else if (hh >= 12 && hh <= 14) {
            System.out.println("中午");
        } else if (hh >= 15 && hh <= 18) {
            System.out.println("下午");
        } else if (hh >= 19 && hh <= 24) {
            System.out.println("晚上");
        }
    }


}
