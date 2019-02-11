package com.edunetcracker.startreker.service;

import com.edunetcracker.startreker.dao.UserDAO;
import com.edunetcracker.startreker.domain.Role;
import com.edunetcracker.startreker.domain.User;
import com.edunetcracker.startreker.forms.SignUpForm;
import com.edunetcracker.startreker.util.AuthorityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean ifUsernameExist(String username) {
        return userDAO.findByUsername(username).isPresent();
    }

    public boolean ifEmailExist(String email) {
        return userDAO.findByEmail(email).isPresent();
    }

    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email).orElse(null);
    }

    public String changePasswordForUser(User user) {
        String newPassword = "asdasd";
        user.setUserPassword(passwordEncoder.encode(newPassword));
        userDAO.save(user);

        return newPassword;
    }

    public User createUser(SignUpForm signUpForm, boolean isActivated) {
        User user = new User(signUpForm.getUsername(),
                passwordEncoder.encode(signUpForm.getPassword()),
                signUpForm.getEmail());
        user.setUserIsActivated(isActivated);
        user.setUserRoles(createRoles(signUpForm.getIsCarrier()));

        userDAO.save(user);

        return user;
    }

    private List<Role> createRoles(boolean isCarrier) {
        List<Role> roles = new ArrayList<>();
        roles.add(AuthorityUtils.ROLE_USER);
        if (isCarrier) {
            roles.add(AuthorityUtils.ROLE_CARRIER);
        }

        return roles;
    }

}