package com.edunetcracker.startreker.dto;

import com.edunetcracker.startreker.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class UserDTO {

    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    private UserDTO(String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    public static UserDTO from(User user){
        return new UserDTO(user.getUsername(), user.getAuthorities());
    }
}
