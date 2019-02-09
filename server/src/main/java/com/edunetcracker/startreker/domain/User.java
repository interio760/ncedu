package com.edunetcracker.startreker.domain;

import com.edunetcracker.startreker.domain.annotations.Attribute;
import com.edunetcracker.startreker.domain.annotations.PrimaryKey;
import com.edunetcracker.startreker.domain.annotations.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("users")
@NoArgsConstructor
public class User {
    @PrimaryKey("id")
    @EqualsAndHashCode.Include
    private Long userId;
    @Attribute("username")
    private String userName;
    @Attribute("password")
    private String userPassword;

    private List<Role> roles;

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }
}
