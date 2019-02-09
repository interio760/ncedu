package com.edunetcracker.startreker.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    ROLE_ADMIN(1),
    ROLE_USER(2),
    ROLE_CARRIER(3);

    private int id;

}
