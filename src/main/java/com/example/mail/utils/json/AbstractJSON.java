package com.example.mail.utils.json;

import java.util.Date;

/**
 * create by March21.Sunny on 2018/10/26
 * 建立AbstractJSON(JSON数据的响应基类）
 */
public class AbstractJSON {
    /**
     * 响应状态码
     */
    private String code;
    /**
     * 响应状态描述
     */
    private String msg;
    /**
     * 时间戳
     */
    private Long time = new Date().getTime();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


}
