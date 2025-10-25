package com.ikram.service;

import com.ikram.dto.CategoryDto;
import com.ikram.dto.SalonDto;
import com.ikram.dto.ServiceDto;
import com.ikram.model.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDto salonDto, ServiceDto serviceDto, CategoryDto categoryDto);
    ServiceOffering updateService(Long serviceId,ServiceOffering service) throws Exception;
    Set<ServiceOffering> getAllServicesBySalon(Long salonId,Long categoryId);
    Set<ServiceOffering> getServicesByIds(Set<Long> ids);
    ServiceOffering getServiceById(Long serviceId) throws Exception;
}
