package com.keyao.no004completablefutureutil.demos.web;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private String id;
    private String name;
    private Integer age;
}
