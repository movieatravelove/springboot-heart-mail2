package com.example.mail.modules.warm_heart_mail.service;

/**
 * @Author: zhang
 * @Date: 2020/8/27 10:37
 * @Description:
 * @Version: 1.0
 */
public interface SendEmailService {

    /**
     * 发送爱心邮件
     * @param eamil 对方邮件地址
     * @param username 对方名称
     * @param meetDateStr 相遇日期
     * @param cityname 对方城市
     * @param consName 对方星座
     */
    boolean sendHeartEmail(String eamil, String username, String meetDateStr,
                   String cityname, String consName);

}
