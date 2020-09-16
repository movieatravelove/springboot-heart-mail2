package com.example.mail.modules.warm_heart_mail.service;

import com.example.mail.entity.Result;


/**
 * @Author: zhang
 * @Date: 2020/8/27 9:45
 * @Description: Api接口
 * @Version: 1.0
 */
public interface ApiService {

    /**
     * 根据城市名查询天气
     *
     * @param cityname 城市名或城市ID，如："苏州"，需要utf8 urlencode
     */
    Result getWeather(String cityname);


    /**
     * 星座运势
     *
     * @param consName 星座名称，如:双鱼座
     */
    Result getHoroscope(String consName);


    /**
     * 最新笑话
     *
     * @param page     当前页数,默认1
     * @param pagesize 每次返回条数,默认1,最大20
     */
    Result getJoke(int page, int pagesize);

    /**
     * 随机笑话
     */
    Result getRandJoke();


    /**
     * 必应每日一图
     */
    Result getOneImage();

    /**
     * 爱词霸每日一图
     */
    Result getOneNote();


}
