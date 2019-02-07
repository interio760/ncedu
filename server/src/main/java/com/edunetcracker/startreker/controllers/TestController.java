package com.edunetcracker.startreker.controllers;

import com.edunetcracker.startreker.forms.TestForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(@Valid TestForm form) {
        return form.getNumber() + " " + form.getText();
    }

}
