package com.example.mail.modules.warm_heart_mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * @Author: zhang
 * @Date: 2019/1/18 9:56
 * @Description: 邮件发送业务层
 */

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired // 项目启动时将mailSender注入
    private JavaMailSender javaMailSender;

    private MimeMessageHelper helper;
    private MimeMessage message;

    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendTextMail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送对象
        message.setTo(to);
        // 邮件主题
        message.setSubject(subject);
        // 发送对象
        message.setText(content);
        // 邮件的发起者
        message.setFrom(from);

        javaMailSender.send(message);
    }


    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage sendMessage = sendMail(to, subject, content);
        javaMailSender.send(sendMessage);

    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePathList
     * @throws MessagingException
     */
    public void sendAttachmentMail(String to, String subject, String content, String[] filePathList) throws MessagingException {

        MimeMessage sendMessage = sendMail(to, subject, content);

        for (String filePath: filePathList) {
            System.out.println(filePath);

            FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
            String fileName = fileSystemResource.getFilename();
            helper.addAttachment(fileName, fileSystemResource);
        }

        javaMailSender.send(sendMessage);
    }

    /**
     * 发送HTML内嵌图片的邮件
     * @param to
     * @param subject
     * @param content
     * @param srcPath
     * @param srcId
     * @throws MessagingException
     */
    public void sendHtmlInlinePhotoMail(String to, String subject, String content, String srcPath, String srcId) throws MessagingException {

        MimeMessage sendMessage = sendMail(to, subject, content);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(srcPath));
        helper.addInline(srcId, fileSystemResource);

        javaMailSender.send(sendMessage);
    }


    /**
     * 发送邮件公共方法
     * @param to
     * @param subject
     * @param content
     * @return
     * @throws MessagingException
     */
    public MimeMessage sendMail(String to, String subject, String content) throws MessagingException {
        message = javaMailSender.createMimeMessage();
        helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        return message;
    }




}
