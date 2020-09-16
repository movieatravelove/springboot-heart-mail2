package com.example.mail.modules.warm_heart_mail.task;

import com.example.mail.modules.warm_heart_mail.service.impl.SendEmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



/**
 * @Author: zhang
 * @Date: 2019/3/1 14:58
 * @Description: 定时任务发送邮件
 */

@Component
@EnableScheduling
public class HeartEmailTask {

    @Autowired
    private SendEmailServiceImpl sendEmailService;

    /**
     * 每天早上八点发送邮件
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendEmail1() {
        System.out.println("开始发送邮件");
        sendEmailService.sendHeartEmail("892670873@qq.com", "大哥",
                "19950515","西安","双子座");
    }


}
