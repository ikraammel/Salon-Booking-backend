package com.ikram.salon.service.service;

import com.ikram.salon.service.dto.SalonDto;
import com.ikram.salon.service.dto.UserDto;
import com.ikram.salon.service.model.Salon;
import com.ikram.salon.service.repository.SalonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class ISalonService implements SalonService{

    private final SalonRepository salonRepository;
    @Override
    public Salon createSalon(SalonDto request, UserDto user) {
        Salon salon = new Salon();
        salon.setName(request.getName());
        salon.setImages(request.getImages());
        salon.setAddress(request.getAddress());
        salon.setPhoneNumber(request.getPhoneNumber());
        salon.setEmail(request.getEmail());
        salon.setCity(request.getCity());
        salon.setOwnerId(user.getId());
        salon.setOpenTime(request.getOpenTime());
        salon.setCloseTime(request.getCloseTime());
        return salonRepository.save(salon);
    }

    @Override
    public Salon updateSalon(SalonDto request, UserDto user, Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElse(null);
        if(salon !=null && salon.getOwnerId().equals(user.getId())){
            salon.setName(request.getName());
            salon.setImages(request.getImages());
            salon.setAddress(request.getAddress());
            salon.setPhoneNumber(request.getPhoneNumber());
            salon.setEmail(request.getEmail());
            salon.setCity(request.getCity());
            salon.setOwnerId(user.getId());
            salon.setOpenTime(request.getOpenTime());
            salon.setCloseTime(request.getCloseTime());

            return salonRepository.save(salon);
        }
        throw new Exception("Inexistant salon!");
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElse(null);
        if(salon == null){
            throw new Exception("Inexistant salon!");
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSalonByCityName(String city) {
        return salonRepository.searchSalons(city);
    }
}
