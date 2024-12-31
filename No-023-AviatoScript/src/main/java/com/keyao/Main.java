package com.keyao;

import com.googlecode.aviator.AviatorEvaluator;

public class Main {

    /**
     * https://developer.aliyun.com/article/608829
     * @param args
     */
    public static void main(String[] args) {
        String exp = "1+2";
        String result = AviatorEvaluator.execute(exp).toString();
        System.out.println(result);
    }
}
