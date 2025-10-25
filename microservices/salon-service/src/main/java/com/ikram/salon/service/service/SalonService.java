package com.ikram.salon.service.service;

import com.ikram.salon.service.dto.SalonDto;
import com.ikram.salon.service.dto.UserDto;
import com.ikram.salon.service.model.Salon;

import java.util.List;

public interface SalonService {

    Salon createSalon(SalonDto salon, UserDto user);

    Salon updateSalon(SalonDto salonDto,UserDto user,Long salonId) throws Exception;

    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId) throws Exception;

    Salon getSalonByOwnerId(Long ownerId);

    List<Salon> searchSalonByCityName(String city);

}
