package com.keyao.no015flinkcdc.demos.web;

import com.alibaba.fastjson2.JSON;
import com.jayway.jsonpath.JsonPath;

public class JsonUtil {
    public static <T> T getValue(String jsonString, String expression, Class<T> clazz) {
        try {
            Object extractedObject = JsonPath.read(jsonString, "$." + expression);
            String extractedJsonString = JSON.toJSONString(extractedObject);
            return JSON.parseObject(extractedJsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
