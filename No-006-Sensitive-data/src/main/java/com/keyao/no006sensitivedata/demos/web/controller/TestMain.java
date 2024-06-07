package com.keyao.no006sensitivedata.demos.web.controller;

import cn.hutool.core.util.DesensitizedUtil;

public class TestMain {

    public static void main(String[] args) {
        System.out.println(DesensitizedUtil.mobilePhone("18216556791"));
        System.out.println(DesensitizedUtil.carLicense("沪B08U28"));
        System.out.println(DesensitizedUtil.idCardNum("410328200001010007", 3, 4));
        System.out.println(DesensitizedUtil.bankCard("6214856213978533"));

    }
}
