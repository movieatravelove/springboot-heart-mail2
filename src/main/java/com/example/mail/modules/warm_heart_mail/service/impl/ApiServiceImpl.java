package com.example.mail.modules.warm_heart_mail.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mail.entity.Result;
import com.example.mail.modules.warm_heart_mail.service.ApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: zhang
 * @Date: 2019/3/1 11:57
 * @Description: 请求 api,返回数据
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Value("${api.weather.url}")
    private String weatherApiUrl;
    @Value("${api.joke.url}")
    private String jokeApiUrl;
    @Value("${api.joke.url2}")
    private String randJokeApiUrl;
    @Value("${api.horoscope.url}")
    private String horoscopeApiUrl;
    @Value("${api.weather.appKey}")
    private String weatherAppKey;
    @Value("${api.joke.appKey}")
    private String jokeAppKey;
    @Value("${api.horoscope.appKey}")
    private String horoscopeAppKey;
    // 每日一图
    @Value("${api.oneImageUrl}")
    private String oneImageUrl;
    // 每日一句
    @Value("${api.oneNoteUrl}")
    private String oneNoteUrl;

    /**
     * 根据城市名查询天气
     *
     * @param cityname 城市名或城市ID，如："苏州"，需要utf8 urlencode
     */
    @Override
    public Result getWeather(String cityname) {
        Map<String, Object> param = new HashMap<>();
        param.put("cityname", cityname);
        param.put("key", weatherAppKey);
        JSONObject json = JSONObject.parseObject(HttpUtil.get(weatherApiUrl, param));
        if ("200".equals(json.getString("resultcode"))) {
            JSONObject resultJson = json.getJSONObject("result");
            JSONObject today = resultJson.getJSONObject("today");
            JSONObject future = resultJson.getJSONObject("future");
            JSONObject data = new JSONObject(true);
            data.put("today", today);
            data.put("future", future);
            return new Result().success(json.getString("reason"), data);
        } else {
            return new Result().error(json.getString("reason"));
        }
    }


    /**
     * 星座运势
     *
     * @param consName 星座名称，如:双鱼座
     * @return
     */
    @Override
    public Result getHoroscope(String consName) {
        Map<String, Object> param = new HashMap<>();
        param.put("consName", consName);
        param.put("key", horoscopeAppKey);
        param.put("type", "today");   // 运势类型：today,tomorrow,week,month,year
        JSONObject json = JSONObject.parseObject(HttpUtil.get(horoscopeApiUrl, param));
        if (json.getIntValue("error_code") == 0) {
            json.remove("error_code");
            return new Result().success(json);
        } else {
            return new Result().error(json.getString("reason"));
        }
    }


    /**
     * 最新笑话
     *
     * @param page     当前页数,默认1
     * @param pagesize 每次返回条数,默认1,最大20
     * @return
     */
    @Override
    public Result getJoke(int page, int pagesize) {
        Map<String, Object> param = new HashMap<>();
        param.put("page", page);
        param.put("pagesize", pagesize);
        param.put("key", jokeAppKey);
        JSONObject json = JSONObject.parseObject(HttpUtil.get(jokeApiUrl, param));
        if (json.getIntValue("error_code") == 0) {
            JSONObject resultJson = json.getJSONObject("result");
            json.remove("error_code");
            return new Result().success(json.getString("reason"), resultJson.getJSONArray("data"));
        } else {
            return new Result().success(json.getString("reason"));
        }
    }

    /**
     * 随机笑话
     *
     * @return
     */
    @Override
    public Result getRandJoke() {
        Map<String, Object> param = new HashMap<>();
        param.put("key", jokeAppKey);
        JSONObject json = JSONObject.parseObject(HttpUtil.get(randJokeApiUrl, param));
        if (json.getIntValue("error_code") == 0) {
            JSONArray resultJson = json.getJSONArray("result");
            json.remove("error_code");
            return new Result().success(json.getString("reason"), resultJson.getJSONObject(0));
        } else {
            return new Result().error(json.getString("reason"));
        }
    }


    /**
     * 必应每日一图
     *
     * @return
     */
    @Override
    public Result getOneImage() {
        JSONObject json = JSONObject.parseObject(HttpUtil.get(oneImageUrl));
        JSONObject resultJson = json.getJSONArray("images").getJSONObject(0);
        JSONObject data = new JSONObject();
        String imgUrl = "https://cn.bing.com/" + resultJson.getString("url");
        System.out.println("bing img: " + imgUrl);
        data.put("image", imgUrl);
        data.put("copyright", resultJson.getString("copyright"));
        return new Result().success(data);
    }

    /**
     * 爱词霸每日一图
     *
     * @return
     */
    @Override
    public Result getOneNote() {
        JSONObject json = JSONObject.parseObject(HttpUtil.get(oneNoteUrl));
        JSONObject data = new JSONObject();
        // 英文
        data.put("content", json.getString("content"));
        // 中文
        data.put("note", json.getString("note"));
        // 解读
        data.put("translation", json.getString("translation"));
        // 海报
        data.put("fenxiang_img", json.getString("fenxiang_img"));
        return new Result().success(data);
    }


}
