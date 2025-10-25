package com.ikram.controller;

import com.ikram.model.ServiceOffering;
import com.ikram.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;


    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(@PathVariable Long salonId
                ,@RequestParam(required = false) Long categoryId){
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServicesBySalon(salonId,categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceOffering> getServiceById(@PathVariable Long serviceId) throws Exception {
        ServiceOffering service = serviceOfferingService.getServiceById(serviceId);
        return ResponseEntity.ok(service);
    }

    @GetMapping("/multiple")
    public ResponseEntity<Set<ServiceOffering>> getServicesByIds(@RequestParam Set<Long> ids) throws Exception {
        Set<ServiceOffering> services = serviceOfferingService.getServicesByIds(ids);
        return ResponseEntity.ok(services);
    }
}
