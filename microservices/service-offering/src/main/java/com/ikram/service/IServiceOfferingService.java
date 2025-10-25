package com.ikram.service;

import com.ikram.dto.CategoryDto;
import com.ikram.dto.SalonDto;
import com.ikram.dto.ServiceDto;
import com.ikram.model.ServiceOffering;
import com.ikram.repository.ServiceOfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IServiceOfferingService implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    @Override
    public ServiceOffering createService(SalonDto salonDto, ServiceDto serviceDto, CategoryDto categoryDto) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setName(serviceDto.getName());
        serviceOffering.setDescription(serviceDto.getDescription());
        serviceOffering.setPrice(serviceDto.getPrice());
        serviceOffering.setDuration(serviceDto.getDuration());
        serviceOffering.setSalonId(salonDto.getId());
        serviceOffering.setCategoryId(categoryDto.getId());
        serviceOffering.setImages(serviceDto.getImages());
        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(serviceId).orElse(null);
        if (serviceOffering == null){
            throw new Exception("service doesn't exist with id "+serviceId);
        }
        serviceOffering.setName(service.getName());
        serviceOffering.setDescription(service.getDescription());
        serviceOffering.setPrice(service.getPrice());
        serviceOffering.setDuration(service.getDuration());
        serviceOffering.setImages(service.getImages());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public Set<ServiceOffering> getAllServicesBySalon(Long salonId, Long categoryId) {
        Set<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);
        if(categoryId!=null){
            services = services.stream()
                        .filter(service -> service.getCategoryId() != null && service.getCategoryId()
                        .equals(categoryId))
                        .collect(Collectors.toSet());
        }
        return services;
    }

    @Override
    public Set<ServiceOffering> getServicesByIds(Set<Long> ids) {
        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(Long serviceId) throws Exception {
        ServiceOffering serviceOffering = serviceOfferingRepository.findById(serviceId).orElse(null);
        if (serviceOffering == null){
            throw new Exception("service doesn't exist with id "+serviceId);
        }
        return serviceOffering;
    }
}
