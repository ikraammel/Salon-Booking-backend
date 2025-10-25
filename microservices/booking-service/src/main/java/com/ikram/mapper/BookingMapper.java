package com.ikram.mapper;

import com.ikram.dto.BookingDto;
import com.ikram.model.Booking;

public class BookingMapper {

    public static BookingDto toDto(Booking booking){
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setCustomerId(booking.getCustomerId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setStartTime(booking.getStartTime());
        bookingDto.setEndTime(booking.getEndTime());
        bookingDto.setSalonId(booking.getSalonId());
        bookingDto.setServiceIds(booking.getServiceIds());
        return bookingDto;
    }
}
