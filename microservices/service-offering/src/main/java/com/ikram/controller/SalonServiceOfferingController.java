package com.ikram.controller;

import com.ikram.dto.CategoryDto;
import com.ikram.dto.SalonDto;
import com.ikram.dto.ServiceDto;
import com.ikram.model.ServiceOffering;
import com.ikram.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(@RequestBody ServiceDto serviceDto){
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(serviceDto.getCategoryId());

        ServiceOffering serviceOffering = serviceOfferingService.createService(salonDto,serviceDto,categoryDto);

        return ResponseEntity.ok(serviceOffering);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceOffering> updateService(@PathVariable Long serviceId,
                                                         @RequestBody ServiceOffering serviceOffering) throws Exception {
        ServiceOffering service = serviceOfferingService.updateService(serviceId,serviceOffering);
        return ResponseEntity.ok(service);
    }
}
