package com.sender.sparta.core.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true, fluent = true)
public class PageModel {
    private Integer current;
    private Integer size;
    private Integer pages;
    private Long total;
    private List<?> records;
}
