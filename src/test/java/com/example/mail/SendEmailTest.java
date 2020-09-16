package com.example.mail;

import com.example.mail.modules.warm_heart_mail.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * @Author: zhang
 * @Date: 2019/1/30 15:56
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendEmailTest {

    @Autowired
    private MailService mailService;
    @Resource
    TemplateEngine templateEngine;

    @Test
    public void mail() throws MessagingException {
        String htmlContent = "<html><body><h1 style='color:red'>这是一封Springboot发送的HTML邮件</h1></body><html/>";

        String[] failPaths = new String[2];
        failPaths[0] = "D:\\Java\\IdeaProjects\\mail\\src\\main\\resources\\static\\layui\\images\\userface1.jpg";
        failPaths[1] = "D:\\Java\\IdeaProjects\\mail\\src\\main\\resources\\static\\layui\\images\\userface2.jpg";


        String srcPaths = "D:\\Java\\IdeaProjects\\mail\\src\\main\\resources\\static\\layui\\images\\userface2.jpg";
        String srcId = "userface2";
        String srcHtmlContent = "<html><body>" +
                "<h3>这是一封Springboot发送的HTML 带图片邮件</h1>" +
                "<img src='cid:"+srcId+"'></img>" +
                "</body><html/>";
//        mailService.sendTextMail("892670873@qq.com","测试","Spring boot mail test send~");

//        mailService.sendHtmlMail("892670873@qq.com","html测试",htmlContent);
//
//        mailService.sendAttachmentMail("892670873@qq.com","html测试","请查看附件信息",failPaths);

        mailService.sendHtmlInlinePhotoMail("892670873@qq.com","html图片测试",
                srcHtmlContent,srcPaths,srcId);
    }

    @Test
    public void testTemplateEmailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("username", "张三");

        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail("892670873@qq.com", "发送模板邮件", emailContent);
    }
}
