package com.ikram.service;

import com.ikram.domain.BookingStatus;
import com.ikram.dto.BookingRequest;
import com.ikram.dto.SalonDto;
import com.ikram.dto.ServiceDto;
import com.ikram.dto.UserDto;
import com.ikram.model.Booking;
import com.ikram.model.SalonReport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDto user, SalonDto salon, Set<ServiceDto> serviceDtos) throws Exception;
    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBySalon(Long salonId);
    Booking getBookingById(Long bookingId) throws Exception;
    Booking updateBookingStatus(Long bookingId, BookingStatus status) throws Exception;
    List<Booking> getBookingsByDate(LocalDate date,Long salonId);
    SalonReport getSalonReport(Long salonId);

}
