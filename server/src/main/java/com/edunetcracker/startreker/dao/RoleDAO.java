package com.edunetcracker.startreker.dao;

import com.edunetcracker.startreker.domain.Role;
import com.edunetcracker.startreker.domain.mapper.RoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDAO extends CrudDAO<Role> {

    private final String findByUsernameSql = "SELECT * FROM usr WHERE user_name = ?";

    public List<Role> getRolesOfUser(long userId) {
        return getJdbcTemplate().query("SELECT roles.*\n" +
                        "FROM users\n" +
                        "INNER JOIN users_roles ON users.id = users_roles.user_id\n" +
                        "INNER JOIN roles ON roles.id = users_roles.role_id\n" +
                        "WHERE users.id = ?",
                new Object[]{userId},
                new RoleMapper());
    }

}
