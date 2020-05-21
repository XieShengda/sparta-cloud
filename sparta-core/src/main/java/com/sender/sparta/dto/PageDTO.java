package com.sender.sparta.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Accessors(fluent = true)
public class PageDTO implements Page {
    @Min(1)
    @NotNull
    private Integer current;
    @Min(1)
    @Max(50)
    private Integer size = 5;
}
