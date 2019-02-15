package com.edunetcracker.startreker.domain;

import com.edunetcracker.startreker.dao.annotations.Attribute;
import com.edunetcracker.startreker.dao.annotations.PrimaryKey;
import com.edunetcracker.startreker.dao.annotations.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("planet")
public class Planet {

    @PrimaryKey("planet_id")
    @EqualsAndHashCode.Include
    private Long planetId;

    @Attribute("planet_name")
    private String planetName;
}
