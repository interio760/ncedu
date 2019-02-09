package com.edunetcracker.startreker.dao;

import com.edunetcracker.startreker.domain.Role;
import com.edunetcracker.startreker.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Test {

    @Autowired
    RoleDAO roleDAO;
    @Autowired
    UserDAO userDAO;

    @PostConstruct
    public void asd() {
        User user = userDAO.find(4).get();
        for(GrantedAuthority ga : user.getAuthorities()){
            System.out.println(ga.getAuthority());
        }
    }
}
