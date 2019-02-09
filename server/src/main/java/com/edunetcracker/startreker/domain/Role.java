package com.edunetcracker.startreker.domain;

import com.edunetcracker.startreker.domain.annotations.Attribute;
import com.edunetcracker.startreker.domain.annotations.PrimaryKey;
import com.edunetcracker.startreker.domain.annotations.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("roles")
public class Role {
    @PrimaryKey("id")
    @EqualsAndHashCode.Include
    private Long roleId;
    @Attribute("role")
    private String roleName;
}
