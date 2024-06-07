package com.keyao.no011mybatisplusdelete.demos.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName("user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -8547973828264286407L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String name;

    private Integer age;

    private Integer deleted;
}
