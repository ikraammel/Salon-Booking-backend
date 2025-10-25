package com.ikram.dto;

import com.ikram.domain.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {

    private Long id;

    private Long salonId;

    private Long customerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Set<Long> serviceIds ;

    private BookingStatus status = BookingStatus.PENDING;

    private int totalPrice;
}
