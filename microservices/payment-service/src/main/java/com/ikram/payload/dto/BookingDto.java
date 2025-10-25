package com.ikram.payload.dto;

import com.ikram.domain.PaymentOrderStatus;
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

    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    private int totalPrice;
}
