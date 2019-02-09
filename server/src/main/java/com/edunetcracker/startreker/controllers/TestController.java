package com.edunetcracker.startreker.controllers;

import com.edunetcracker.startreker.dao.UserDAO;
import com.edunetcracker.startreker.domain.User;
import com.edunetcracker.startreker.forms.TestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping("/api/test")
    public String test(@Valid TestForm form) {
        return form.getNumber() + " " + form.getText();
    }


    @GetMapping("/api/test/db")
    public User testDB() {
        return userDAO.findByName("vitya");
    }
}
