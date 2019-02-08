package com.edunetcracker.startreker.dao;

import com.edunetcracker.startreker.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoleDAO extends CrudDAO<Role> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Role.class);
        this.jdbcTemplate = jdbcTemplate;
    }

}
