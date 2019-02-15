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
@Table("ticket_class")
public class TicketClass {

    @PrimaryKey("ticket_id")
    @EqualsAndHashCode.Include
    private Long ticketId;

    @Attribute("trip_id")
    private Long tripId;

    @Attribute("ticket_price")
    private Integer ticket_price;

}
