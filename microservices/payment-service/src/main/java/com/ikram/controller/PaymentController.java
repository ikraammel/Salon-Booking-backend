package com.ikram.controller;

import com.ikram.domain.PaymentMethod;
import com.ikram.model.PaymentOrder;
import com.ikram.payload.PaymentLinkResponse;
import com.ikram.payload.dto.BookingDto;
import com.ikram.payload.dto.UserDto;
import com.ikram.service.PaymentService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDto bookingDto,
                                                                 @RequestParam PaymentMethod paymentMethod) throws RazorpayException {

        UserDto userDto = new UserDto();
        userDto.setFullName("Ashok");
        userDto.setEmail("ashok@gmail.com");
        userDto.setId(1L);

        PaymentLinkResponse resp = paymentService.createOrder(userDto,bookingDto,paymentMethod);
        return ResponseEntity.ok(resp);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(@PathVariable Long paymentOrderId) throws Exception {

        PaymentOrder resp = paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<Boolean> processPayment(@RequestParam String paymentId,
                                                       @RequestParam String paymentLinkId) throws Exception {
        PaymentOrder  paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentId);
        Boolean resp = paymentService.proceedPayment(paymentOrder,paymentId,paymentLinkId);
        return ResponseEntity.ok(resp);
    }
}
