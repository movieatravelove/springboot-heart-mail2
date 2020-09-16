package com.example.mail.modules.warm_heart_mail.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.mail.entity.Result;
import com.example.mail.modules.warm_heart_mail.component.SaveEchartsComponent2;
import com.example.mail.modules.warm_heart_mail.service.ApiService;
import com.example.mail.modules.warm_heart_mail.service.MailService;
import com.example.mail.modules.warm_heart_mail.service.SendEmailService;
import com.example.mail.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * @Author: zhang
 * @Date: 2019/3/1 15:04
 * @Description:
 */
@Service
public class SendEmailServiceImpl implements SendEmailService {
    @Resource
    TemplateEngine templateEngine;
    @Autowired
    private MailService mailService;
    @Autowired
    private ApiService apiService;
    @Autowired
    private SaveEchartsComponent2 saveEchartsComponent2;

    String[] YI = {"努力", "挣钱", "睡觉", "开心", "放松", "表白", "细心", "理智", "乐观",
            "吃火锅", "自信", "大胆"};

    /**
     * 发送爱心邮件
     *
     * @param eamil       对方邮件地址
     * @param username    对方名称
     * @param meetDateStr 相遇日期
     * @param cityname    对方城市
     * @param consName    对方星座
     */
    @Override
    public boolean sendHeartEmail(String eamil, String username, String meetDateStr,
                                  String cityname, String consName) {
        try {
            Context context = new Context();
            // 当前时间段
            String noon = DateUtil.getTimeNoon();
            context.setVariable("noon", noon + "好");
            Date meetDate = DateUtil.stringtoDate(meetDateStr, DateUtil.FORMAT_FOUR);
            long meetDay = DateUtil.dayDiff(meetDate, new Date());
            context.setVariable("username", username);
            context.setVariable("dayCount", meetDay);
            context.setVariable("day", DateUtil.dateToString(new Date(), DateUtil.FORMAT_FIVE));
            context.setVariable("week", DateUtil.getWeek(new Date()));
            context.setVariable("yi", "宜"+YI[new Random().nextInt(12)-1]);
            // 天气信息
            Result weatherRoot = apiService.getWeather(cityname);
            if (weatherRoot.getCode() == 0) {
                JSONObject data = (JSONObject) weatherRoot.getData();
                JSONObject today = data.getJSONObject("today");
                String weather = today.getString("weather");
                String week = today.getString("week");
                String temperature = today.getString("temperature");
                String advice = StringUtils.substringBefore(today.getString("dressing_advice"), "。");
                JSONObject future = data.getJSONObject("future");
//                for (int i = 0; i < 3; i++) {
//                    String weaDate = DateUtil.dateToString(DateUtil.getDateAfter(new Date(), i),
//                            DateUtil.FORMAT_FOUR);
//                    JSONObject currWea = future.getJSONObject("day_" + weaDate);
//                    currWea.remove("weather_id");
//                    List<JSONObject> currWeaList = JsonUtils.
//                            jsonToList("[" + currWea.toJSONString() + "]", JSONObject.class);
//                    context.setVariable("currWeaList" + i, currWeaList);
//                }
                context.setVariable("city", cityname);
                context.setVariable("weather", weather);
                context.setVariable("temperature", temperature);
                context.setVariable("advice", advice);
            }
            // 星座运势
            Result consRoot = apiService.getHoroscope(consName);
            if (consRoot.getCode() == 0) {
                JSONObject data = (JSONObject) consRoot.getData();
                context.setVariable("consName", data.getString("name"));
                String summary = data.getString("summary");
                summary =  summary.substring(0, summary.lastIndexOf("。"));
                // 分割成数组遍历
                context.setVariable("summary", summary.split("，|。"));
            }
            // 星座雷达图
//            String radarImg = saveEchartsComponent2.saveSurfModelUrlToImg(consName);
//            context.setVariable("radarImg", radarImg);


            // 随机笑话
            Result randJockRoot = apiService.getRandJoke();
            if (randJockRoot.getCode() == 0) {
                JSONObject data = (JSONObject) randJockRoot.getData();
                String jock = data.getString("content");
                context.setVariable("jock", jock);
            }

            // 每日一图
            Result oneImageRoot = apiService.getOneImage();
            if (oneImageRoot.getCode() == 0) {
                JSONObject data = (JSONObject) oneImageRoot.getData();
                context.setVariable("image", data.getString("image"));
                context.setVariable("copyright", data.getString("copyright"));
            }

            // 每日一句
            Result oneNoteRoot = apiService.getOneNote();
            if (oneNoteRoot.getCode() == 0) {
                JSONObject data = (JSONObject) oneNoteRoot.getData();
                context.setVariable("content", data.getString("content"));
                context.setVariable("note", data.getString("note"));
                context.setVariable("translation", data.getString("translation"));
            }

            String emailContent = templateEngine.process("eamilPage/heart_email", context);
            mailService.sendHtmlMail(eamil, "一封暖暖的小邮件", emailContent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
    String aa ="今天处女座在财务问题上会有些许的紧张与压力，你可能会因为家庭而需要支付一笔钱，事业上的不顺心也让你倍感委屈。不要担心，这都是短暂的过程，一切都会慢慢好起来的。作者：伦敦占星学院-占星师萦渝";
       aa =  aa.substring(0, aa.lastIndexOf("。"));
        String[] split = aa.split("，|。");
        for (String s : split) {
            System.out.println(s);
        }
    }
}
