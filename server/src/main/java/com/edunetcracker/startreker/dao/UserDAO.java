package com.edunetcracker.startreker.dao;

import com.edunetcracker.startreker.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDAO extends CrudDAO<User> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, User.class);
        this.jdbcTemplate = jdbcTemplate;
    }
}
