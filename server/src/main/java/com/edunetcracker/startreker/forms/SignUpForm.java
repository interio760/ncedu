package com.edunetcracker.startreker.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {

    private String username;
    private String password;
    private String email;
    private Boolean is_carrier;
}
