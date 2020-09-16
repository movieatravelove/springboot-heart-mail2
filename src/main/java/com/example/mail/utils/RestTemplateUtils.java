package com.example.mail.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * RestTemplate 远程调用工具类
 */
@Component
public class RestTemplateUtils {


    public static RestTemplate geTemplate(){
        return new RestTemplate(new HttpsClientRequestFactory());
    }

    /**
     * GET请求调用方式
     * @Author mazq
     * @Date 2020/06/01 13:47
     * @Param [url, responseType, uriVariables]
     * @return org.springframework.http.ResponseEntity<T>
     */
    public static <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        return geTemplate().getForEntity(url, responseType, uriVariables);
    }

    /**
     * POST请求调用方式
     * @Author mazq
     * @Date 2020/06/01 13:47
     * @Param [url, headers, body, responseType]
     * @return org.springframework.http.ResponseEntity<T>
     */
    public static <T> ResponseEntity<T> postForEntity(String url,HttpHeaders headers, Object requestBody , Class<T> responseType ){

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return  geTemplate().postForEntity(url, requestEntity, responseType);
    }

    /**
     * PUT请求调用方式
     * @Author mazq
     * @Date 2020/06/01 13:35
     * @param url 请求URL
     * @param headers 请求头参数
     * @param requestBody 请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，与Map中的key对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> put(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return geTemplate().exchange(url, HttpMethod.PUT, requestEntity, responseType, uriVariables);
    }

    /**
     * DELETE请求调用方式
     * @Author mazq
     * @Date 2020/06/01 13:37
     * @param url 请求URL
     * @param headers 请求头参数
     * @param requestBody 请求参数体
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，按顺序依次对应
     * @return ResponseEntity 响应对象封装类
     */
    public static <T> ResponseEntity<T> delete(String url, HttpHeaders headers, Object requestBody, Class<T> responseType, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestBody, headers);
        return geTemplate().exchange(url, HttpMethod.DELETE, requestEntity, responseType, uriVariables);
    }

    /**
     * 通用调用方式
     * @Author mazq
     * @Date 2020/06/01 13:37
     * @Param [url, method, requestEntity, responseType, uriVariables]
     * @return org.springframework.http.ResponseEntity<T>
     */
    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
        return geTemplate().exchange(url, method, requestEntity, responseType, uriVariables);
    }
}

