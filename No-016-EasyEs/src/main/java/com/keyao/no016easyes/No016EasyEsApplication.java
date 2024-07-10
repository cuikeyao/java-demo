package com.keyao.no016easyes;

import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EsMapperScan("com.keyao.no016easyes.demos.web")
@SpringBootApplication
public class No016EasyEsApplication {

    public static void main(String[] args) {
        SpringApplication.run(No016EasyEsApplication.class, args);
    }

}
