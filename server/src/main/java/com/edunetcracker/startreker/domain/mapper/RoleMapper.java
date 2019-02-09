package com.edunetcracker.startreker.domain.mapper;


import com.edunetcracker.startreker.domain.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class RoleMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();
        role.setRoleId(resultSet.getLong("id"));
        role.setRoleName(resultSet.getString("role"));
        return role;
    }
}
