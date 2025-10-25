package com.ikram.salon.service.mapper;

import com.ikram.salon.service.dto.SalonDto;
import com.ikram.salon.service.model.Salon;

public class SalonMapper {

    public static SalonDto mapToSalonDto(Salon salon){
        SalonDto salonDto = new SalonDto();
        salonDto.setId(salon.getId());
        salonDto.setName(salon.getName());
        salonDto.setImages(salon.getImages());
        salonDto.setAddress(salon.getAddress());
        salonDto.setPhoneNumber(salon.getPhoneNumber());
        salonDto.setEmail(salon.getEmail());
        salonDto.setCity(salon.getCity());
        salonDto.setOwnerId(salon.getOwnerId());
        salonDto.setOpenTime(salon.getOpenTime());
        salonDto.setCloseTime(salon.getCloseTime());
        return salonDto;
    }
}
