package com.sender.sparta.core.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class IdDTO {
    @NotNull
    @Min(1)
    private Long id;
}
