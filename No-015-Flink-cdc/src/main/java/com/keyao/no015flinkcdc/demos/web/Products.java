package com.keyao.no015flinkcdc.demos.web;

import lombok.Builder;
import lombok.Data;
import org.apache.flink.types.RowKind;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
public class Products implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String description;
    private RowKind rowKind;
}
