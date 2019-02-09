package com.edunetcracker.startreker.security.customUserDelails;

import com.edunetcracker.startreker.dao.UserDAO;
import com.edunetcracker.startreker.domain.Role;
import com.edunetcracker.startreker.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailImpl implements CustomUserDetails {

    private final UserDAO userDao;

    @Autowired
    public UserDetailImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Invalid username or password."));
    }
}
