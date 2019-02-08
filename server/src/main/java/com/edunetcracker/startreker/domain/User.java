package com.edunetcracker.startreker.domain;

import com.edunetcracker.startreker.domain.annotations.Attribute;
import com.edunetcracker.startreker.domain.annotations.PrimaryKey;
import com.edunetcracker.startreker.domain.annotations.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("usr")
public class User {
    @PrimaryKey("user_id")
    @EqualsAndHashCode.Include
    private Long userId;
    @Attribute("user_name")
    private String userName;
    @Attribute("user_password")
    private String userPassword;
}
