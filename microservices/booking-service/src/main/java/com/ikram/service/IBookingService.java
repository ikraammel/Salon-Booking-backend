package com.ikram.service;

import com.ikram.domain.BookingStatus;
import com.ikram.dto.BookingRequest;
import com.ikram.dto.SalonDto;
import com.ikram.dto.ServiceDto;
import com.ikram.dto.UserDto;
import com.ikram.model.Booking;
import com.ikram.model.SalonReport;
import com.ikram.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IBookingService implements BookingService{

    private final BookingRepository bookingRepository;
    @Override
    public Booking createBooking(BookingRequest booking, UserDto user, SalonDto salon, Set<ServiceDto> serviceDtos) throws Exception {
        int totalDuration =serviceDtos.stream().mapToInt(ServiceDto::getDuration).sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salon,bookingStartTime,bookingEndTime);

        int totalPrice = serviceDtos.stream()
                .mapToInt(ServiceDto::getPrice).sum();
        Set<Long> idList = serviceDtos.stream().map(ServiceDto::getId).collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SalonDto salonDto, LocalDateTime bookingStartTime,LocalDateTime bookingEndTime) throws Exception {
        List<Booking> existingBookings = getBookingsBySalon(salonDto.getId());

        LocalDateTime salonOpenTime = salonDto.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDto.getCloseTime().atDate(bookingEndTime.toLocalDate());

        if(bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)){
            throw new Exception("Booking time must be within salon's working hours");
        }
        for(Booking booking:existingBookings){
            LocalDateTime existingBookingStartTime = booking.getStartTime();
            LocalDateTime existingBookingEndTime = booking.getEndTime();
            if(bookingStartTime.isBefore(existingBookingEndTime) && bookingEndTime.isAfter(existingBookingStartTime)){
                throw new Exception("slot not available, choose different time.");
            }
            if(bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)){
                throw new Exception("slot not available, choose different time.");
            }
        }
        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if(booking == null){
            throw new Exception("booking not found");
        }
        return booking;
    }

    @Override
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);
        if(date == null){
            return bookings;
        }

        return bookings.stream().filter(booking -> isSameDate(booking.getStartTime(),date) ||
                isSameDate(booking.getEndTime(),date)).collect(Collectors.toList());

    }
    private boolean isSameDate(LocalDateTime dateTime, LocalDate date){
        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getBookingsBySalon(salonId);

        int totalEarnings = bookings.stream().mapToInt(Booking::getTotalPrice).sum();
        Integer totalBookings = bookings.size();

        List<Booking> cancelledBookings = bookings.stream()
                                                  .filter(booking -> booking.getStatus()
                                                  .equals(BookingStatus.CANCELED))
                                                  .toList();
        Double totalRefund = cancelledBookings.stream().mapToDouble(Booking::getTotalPrice).sum();

        SalonReport salonReport = new SalonReport();
        salonReport.setSalonId(salonId);
        salonReport.setTotalEarnings(totalEarnings);
        salonReport.setTotalBookings(totalBookings);
        salonReport.setCanceledBookings(cancelledBookings.size());
        salonReport.setTotalRefund(totalRefund);
        return salonReport;
    }
}
