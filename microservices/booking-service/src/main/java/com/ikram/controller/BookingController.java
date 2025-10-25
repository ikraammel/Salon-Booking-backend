package com.ikram.controller;

import com.ikram.domain.BookingStatus;
import com.ikram.dto.*;
import com.ikram.mapper.BookingMapper;
import com.ikram.model.Booking;
import com.ikram.model.SalonReport;
import com.ikram.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Long salonId, @RequestBody BookingRequest booking) throws Exception {
        UserDto user = new UserDto();
        user.setId(1L);

        SalonDto salonDto = new SalonDto();
        salonDto.setId(salonId);
        salonDto.setOpenTime(LocalTime.of(9, 0)); // 09:00
        salonDto.setCloseTime(LocalTime.of(20, 0)); // 20:00

        Set<ServiceDto> services = new HashSet<>();

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(1L);
        serviceDto.setPrice(399);
        serviceDto.setDuration(45);
        serviceDto.setName("Haircut for men");
        services.add(serviceDto);

        Booking newBooking = bookingService.createBooking(booking,user,salonDto,services);

        return ResponseEntity.ok(newBooking);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDto>> getBookingsByCustomer(@RequestParam Long customerId){
        UserDto user = new UserDto();
        user.setId(1L);

        List<Booking> bookings = bookingService.getBookingsByCustomer(customerId);
        return ResponseEntity.ok(getBookingDtos(bookings));
    }
    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDto>> getBookingBySalon(@RequestParam Long salonId){
        UserDto user = new UserDto();
        user.setId(1L);

        List<Booking> bookings = bookingService.getBookingsBySalon(salonId);
        return ResponseEntity.ok(getBookingDtos(bookings));
    }

    private Set<BookingDto> getBookingDtos(List<Booking> bookings){
        return bookings.stream().map(booking -> {
            return BookingMapper.toDto(booking);
        }).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long bookingId) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDto> updateBookingStatus(@PathVariable Long bookingId,
                                                          @RequestParam BookingStatus status) throws Exception {
        Booking booking = bookingService.updateBookingStatus(bookingId,status);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<DateDto>> getBookingByDate(@PathVariable Long salonId,
                                                       @RequestParam(required = false) LocalDate date) throws Exception {
        List<Booking> bookings = bookingService.getBookingsByDate(date,salonId);
        List<DateDto> slotDtos = bookings.stream().map(booking ->{
            DateDto slotDto = new DateDto();
            slotDto.setStartTime(booking.getStartTime());
            slotDto.setEndTime(booking.getEndTime());
            return slotDto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(slotDtos);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport() throws Exception {

        SalonReport report = bookingService.getSalonReport(1L);

        return ResponseEntity.ok(report);
    }
}
