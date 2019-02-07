package com.edunetcracker.startreker.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TestForm {
    @NotNull(message = "{testform.number.notnull}")
    private Integer number;
    @NotBlank(message = "{testform.text.notblank}")
    private String text;
}
