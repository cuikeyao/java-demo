package com.keyao.no014functionalinterfaceifelse.demos.web;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class BasicService {

    private Map<String, Supplier<String>> eatMap = new HashMap<>(){{
        put("cat", () -> new Cat().eat());
        put("dog", () -> new Dog().eat());
    }};

    public String eat1(String animal) {
        if (animal.equals("dog")) {
            return new Dog().eat();
        } else if (animal.equals("cat")) {
            return new Cat().eat();
        } else {
            return "error";
        }
    }

    public String eat2(String animal) {
        Supplier<String> result = eatMap.get(animal);

        if (result != null) {
            return result.get();
        } else {
            return "error";
        }
    }
}
