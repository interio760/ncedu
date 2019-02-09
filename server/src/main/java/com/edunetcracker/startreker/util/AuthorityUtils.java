package com.edunetcracker.startreker.util;

import com.edunetcracker.startreker.dao.RoleDAO;
import com.edunetcracker.startreker.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AuthorityUtils {

    private RoleDAO roleDAO;
    public static Role ROLE_ADMIN;
    public static Role ROLE_USER;
    public static Role ROLE_CARRIER;

    @Autowired
    public AuthorityUtils(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @PostConstruct
    public void init(){
        ROLE_ADMIN = roleDAO.find(1).get();
        ROLE_USER = roleDAO.find(2).get();
        ROLE_CARRIER = roleDAO.find(3).get();
    }
}
