package com.edunetcracker.startreker.dto;

import lombok.Getter;
import org.springframework.validation.ObjectError;

@Getter
public class ValidationExceptionDTO {
    private String inputname;
    private String message;

    public static ValidationExceptionDTO from(ObjectError error){
        ValidationExceptionDTO ex = new ValidationExceptionDTO();
        ex.inputname = error.getObjectName();
        ex.message = error.getDefaultMessage();
        return ex;
    }
}
