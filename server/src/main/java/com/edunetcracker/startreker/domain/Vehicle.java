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
@Table("vehicle")
public class Vehicle {

    @PrimaryKey("vehicle_id")
    @EqualsAndHashCode.Include
    private Long vehicleId;

    @Attribute("owner_id")
    private Long ownerId;

    @Attribute("vehicle_name")
    private String vehicle_name;

    @Attribute("vehicle_seats")
    private Integer vehicle_seats;
}
