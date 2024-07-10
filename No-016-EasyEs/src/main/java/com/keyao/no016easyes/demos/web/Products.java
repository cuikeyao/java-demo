package com.keyao.no016easyes.demos.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.FieldType;
import org.dromara.easyes.annotation.rely.IdType;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@Data
@IndexName("products")
public class Products implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @IndexId(type = IdType.CUSTOMIZE)
    private Integer id;
    @IndexField(fieldType = FieldType.TEXT, analyzer = "ik_smart")
    private String name;
    @IndexField(fieldType = FieldType.TEXT, analyzer = "ik_smart")
    private String description;
}
