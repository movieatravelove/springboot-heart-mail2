package com.example.mail.modules.warm_heart_mail.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mail.entity.Result;
import com.example.mail.modules.warm_heart_mail.service.ApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @Author: zhang
 * @Date: 2019/3/1 14:25
 * @Description:
 */
@ApiIgnore
@Controller
@RequestMapping("/heart")
public class EmailPageController {

    @Autowired
    private ApiService apiService;

    /**
     * @param cityname
     */
    @RequestMapping(value = "/getWeather", method = RequestMethod.GET)
    public String getWeather(Model model,
                             @RequestParam(required = true) String cityname) {
        String page = "eamilPage/error";
        if (cityname != null) {
            Result root = apiService.getWeather(cityname);
            model.addAttribute("data", root);
            if (root.getCode() == 0) {
                page = "eamilPage/heart_email";
            }
        } else {
            JSONObject root = new JSONObject();
            root.put("errorcode", 500);
            root.put("message", "参数异常");
            model.addAttribute("data", root);
            page = "eamilPage/error";
        }
        return page;
    }


    /**
     * 星座雷达
     *
     * @throws Exception
     */
    @RequestMapping(value = "/cons", method = RequestMethod.GET)
    public String getHoroscope(Model model, String consName)  {
        // 星座运势
        Result consRoot = apiService.getHoroscope(consName);
        if (consRoot.getCode() == 0) {
            JSONObject data = (JSONObject) consRoot.getData();
            // 重新赋值，百分制改为数值
            data.put("health", Integer.parseInt(StringUtils.substringBefore(data.getString("health"), "%")));
            data.put("love", Integer.parseInt(StringUtils.substringBefore(data.getString("love"), "%")));
            data.put("money", Integer.parseInt(StringUtils.substringBefore(data.getString("money"), "%")));
            data.put("work", Integer.parseInt(StringUtils.substringBefore(data.getString("work"), "%")));
            model.addAttribute("data", data);
        }
        return "eamilPage/heart_email_cons";
    }
}