package com.ikram.service;

import com.ikram.domain.PaymentMethod;
import com.ikram.model.PaymentOrder;
import com.ikram.payload.dto.BookingDto;
import com.ikram.payload.dto.UserDto;
import com.ikram.payload.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;


public interface PaymentService {

    PaymentLinkResponse createOrder(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethod) throws RazorpayException;
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String paymentId);
    PaymentLink createRazorpayPaymentLink(UserDto userDto,Long amount,Long orderId) throws RazorpayException;
    String createStripePaymentLink(UserDto userDto,Long amount,Long orderId) throws StripeException;
    Boolean proceedPayment(PaymentOrder paymentOrder,String paymentId,String paymentLinkId) throws RazorpayException;
}
