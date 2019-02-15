package com.edunetcracker.startreker.domain;

import com.edunetcracker.startreker.dao.annotations.Attribute;
import com.edunetcracker.startreker.dao.annotations.PrimaryKey;
import com.edunetcracker.startreker.dao.annotations.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("trip")
public class Trip {
    @PrimaryKey("trip_id")
    @EqualsAndHashCode.Include
    private Long tripId;

    @Attribute("vehicle_id")
    private Long vehicle_id;

    @Attribute("trip_status")
    private Integer tripStatus;

    @Attribute("creation_date")
    private LocalDateTime creationDate;

//    other attributes will come later
}
