package com.keyao.no012mybatisplusulid.demos.web;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@TableName(value = "no_012")
@Data
public class Ulid implements Serializable {

    @Serial
    private static final long serialVersionUID = -6628587487101403987L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
}
