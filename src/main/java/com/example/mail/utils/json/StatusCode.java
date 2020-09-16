package com.example.mail.utils.json;

/**
 * create by March21.Sunny on 2018/10/26
 * 定义状态码
 */
public class StatusCode {
    public static String CODE_SUCCESS = "0";          //访问成功

    public static String CODE_ERROR = "0001";          //访问错误

    public static String CODE_ERROR_PARAMETER = "0002";//参数错误

    public static String CODE_ERROR_PROGRAM = "0003";  //程序异常

    public static String CODE_ERROR_NO_LOGIN_OR_TIMEOUT = "0004";  //未登录或登录超时,请重新登录

    public static String CODE_ERROR_EXIST_OPERATION = "0005";      //已操作
}
