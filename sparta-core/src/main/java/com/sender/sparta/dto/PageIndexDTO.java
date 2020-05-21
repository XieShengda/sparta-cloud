package com.sender.sparta.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PageIndexDTO {
    @NotNull
    private Integer index;
    @Min(1)
    @Max(100)
    @NotNull
    private Integer size;
}