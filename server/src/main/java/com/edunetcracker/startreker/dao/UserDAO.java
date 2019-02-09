package com.edunetcracker.startreker.dao;

import com.edunetcracker.startreker.domain.Role;
import com.edunetcracker.startreker.domain.User;
import com.edunetcracker.startreker.domain.mapper.UserMapper;
import com.edunetcracker.startreker.util.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDAO extends CrudDAO<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private JdbcTemplate jdbcTemplate;

    private RoleDAO roleDAO;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        super(jdbcTemplate, User.class);
        this.jdbcTemplate = jdbcTemplate;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user, List<String> stringRoles) {
        user.setUserId(getNextId());
        user.setRoles(getRoles(stringRoles));
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        super.save(user);

        user.getRoles().forEach(role -> saveInMapTable(user.getUserId(), role.getRoleId()));
    }

    public User findByName(String userName) {
        User user;

        try {
            user = (User) jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?",
                    new Object[]{userName},
                    new UserMapper());
            List<Role> roles = roleDAO.getRolesOfUser(user.getUserId());

            user.setRoles(roles);
        } catch (Exception e) {
            logger.error("Can't retrieve user " + userName + " Message ", e);
            user = null;
        }
        return user;
    }

    private void saveInMapTable(long userId, long roleId) {
        jdbcTemplate.update("insert into users_roles (user_id, role_id) values (?, ?)",
                userId, roleId);
    }

    private long getNextId() {
        return jdbcTemplate.queryForObject("SELECT users_pk_seq.NEXTVAL FROM dual", Long.class);
    }

    private List<Role> getRoles(List<String> stringRoles) {
        return convertRoles(stringRoles).
                stream().
                map(roleDAO::find).
                filter(Optional::isPresent).
                map(Optional::get).
                collect(Collectors.toList());
    }

    private List<Integer> convertRoles(List<String> stringRoles) {
        return stringRoles.stream().map(stringRole -> UserRole.valueOf(stringRole).getId()).collect(Collectors.toList());
    }
}
