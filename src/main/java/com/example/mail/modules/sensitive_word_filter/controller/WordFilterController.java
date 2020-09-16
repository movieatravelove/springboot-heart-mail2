package com.example.mail.modules.sensitive_word_filter.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mail.modules.sensitive_word_filter.filter.SensitivewordFilter;
import com.example.mail.modules.sensitive_word_filter.init.SensitiveWordInit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @Author: zhang
 * @Date: 2019/2/28 11:45
 * @Description: 敏感词汇过滤
 */
@Api(tags = "wordFilter", description = "敏感词汇过滤")
@RestController
@RequestMapping("/wordFilter")
public class WordFilterController {

    /**
     * 过滤敏感词汇，并返回
     */
    @ApiOperation("过滤敏感词汇并返回")
    @GetMapping(value = "/fiter")
    @ResponseBody
    public JSONObject wxpay(String str) {
        JSONObject result = new JSONObject();

        SensitivewordFilter filter = new SensitivewordFilter();
        Set<String> set = filter.getSensitiveWord(str, 1);
        // 敏感词汇库中的敏感词汇
        Map sensitiveWordMap = new SensitiveWordInit().initKeyWord();

        result.put("str",str);
        result.put("strSize",str.length());
        result.put("sensitiveWordSize",set.size());
        result.put("sensitiveWord",set);
        result.put("dbSensitiveWordSize",sensitiveWordMap.size());
        result.put("dbSensitiveWord",sensitiveWordMap);
        result.put("filterStr",filter.replaceSensitiveWord(str,1, "*"));

        return result;
    }


}
