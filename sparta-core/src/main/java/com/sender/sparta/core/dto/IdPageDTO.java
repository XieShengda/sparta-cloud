package com.sender.sparta.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class IdPageDTO extends PageDTO {
    @NotNull
    @Min(1)
    private Long id;
}
