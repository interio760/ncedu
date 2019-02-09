package com.edunetcracker.startreker.domain;

import com.edunetcracker.startreker.dao.annotations.Attribute;
import com.edunetcracker.startreker.dao.annotations.PrimaryKey;
import com.edunetcracker.startreker.dao.annotations.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("usr")
public class User implements UserDetails {

    @PrimaryKey("user_id")
    @EqualsAndHashCode.Include
    private Long userId;
    @Attribute("user_name")
    private String userName;
    @Attribute("user_password")
    private String userPassword;

    private List<Role> userRoles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(
                userRoles.stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toList())
                        .toArray(new String[userRoles.size()]));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
