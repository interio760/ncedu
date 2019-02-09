package com.edunetcracker.startreker.domain.mapper;

import com.edunetcracker.startreker.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("id"));
        user.setUserName(resultSet.getString("username"));
        user.setUserPassword(resultSet.getString("password"));
        return user;
    }
}
