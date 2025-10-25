package com.ikram.salon.service.controller;

import com.ikram.salon.service.dto.SalonDto;
import com.ikram.salon.service.dto.UserDto;
import com.ikram.salon.service.mapper.SalonMapper;
import com.ikram.salon.service.model.Salon;
import com.ikram.salon.service.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @PostMapping("/create")
    public ResponseEntity<SalonDto> createSalon(@RequestBody SalonDto dto){
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        Salon salon = salonService.createSalon(dto,userDto);
        SalonDto salonDto = SalonMapper.mapToSalonDto(salon);
        return ResponseEntity.ok(salonDto);
    }

    @PatchMapping("/{salonId}")
    public ResponseEntity<SalonDto> updateSalon(@RequestBody SalonDto request,
                                                @PathVariable Long salonId) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        Salon salon = salonService.updateSalon(request,userDto,salonId);
        SalonDto salonDto = SalonMapper.mapToSalonDto(salon);
        return ResponseEntity.ok(salonDto);
    }

    @GetMapping()
    public ResponseEntity<List<SalonDto>> getAllSalons(){
        List<Salon> salons = salonService.getAllSalons();
        List<SalonDto> salonDtos = salons.stream().map(salon -> {
            SalonDto salonDto  = SalonMapper.mapToSalonDto(salon);
            return salonDto;
        }).toList();
        return ResponseEntity.ok(salonDtos);
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDto> getSalonById(@PathVariable Long salonId) throws Exception {
        Salon salon = salonService.getSalonById(salonId);
        SalonDto salonDto = SalonMapper.mapToSalonDto(salon);
        return ResponseEntity.ok(salonDto);
    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDto> getSalonByOwnerId(@PathVariable Long ownerId) {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        Salon salon = salonService.getSalonByOwnerId(userDto.getId());
        SalonDto salonDto = SalonMapper.mapToSalonDto(salon);
        return ResponseEntity.ok(salonDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDto>> searchSalonByCityName(@RequestParam String city) {
        List<Salon> salons = salonService.searchSalonByCityName(city);
        List<SalonDto> salonDtos = salons.stream().map(salon ->{
            SalonDto salonDto = SalonMapper.mapToSalonDto(salon);
            return salonDto;
        }).toList();
        return ResponseEntity.ok(salonDtos);
    }
}
