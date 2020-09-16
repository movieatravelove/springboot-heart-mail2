package com.example.mail.utils.json;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * create by March21.Sunny on 2018/10/26
 */
public class ResponseUtils {

    /*
     * 返回json串
     */
    public static void renderJson(HttpServletResponse response, String text) {
        // System.out.print(text);
        render(response, "text/plain;charset=UTF-8", text);
    }

    /*
     * 返回文本
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /*
     * 发送内容,使用UTF-8编码
     */
    public static void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
        }
    }

    /*
     * 页面异步回调返回Json
     */
    public static void outputJson(HttpServletResponse response, Object obj) {
        String s = JsonWriter.toJson(obj, false);
        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回json
     * @param response
     * @param list
     * @param code
     * @param msg
     */
    public static void R(HttpServletResponse response, List<?> list, String code, String msg){
        ListObject listObject = new ListObject();
        listObject.setData(list);
        listObject.setCode(code);
        listObject.setMsg(msg);
        ResponseUtils.renderJson(response, JsonUtils.toJson(listObject));
    }
}
