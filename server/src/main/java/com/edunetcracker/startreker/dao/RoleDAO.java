package com.edunetcracker.startreker.dao;

import com.edunetcracker.startreker.domain.Role;
import com.edunetcracker.startreker.domain.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDAO extends CrudDAO<Role> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Role.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Role> getRolesOfUser(long userId) {
        return jdbcTemplate.query("SELECT roles.*\n" +
                        "FROM users\n" +
                        "INNER JOIN users_roles ON users.id = users_roles.user_id\n" +
                        "INNER JOIN roles ON roles.id = users_roles.role_id\n" +
                        "WHERE users.id = ?",
                new Object[]{userId},
                new RoleMapper());
    }
}
