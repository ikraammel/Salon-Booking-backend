package com.ikram.service;

import com.ikram.domain.PaymentMethod;
import com.ikram.domain.PaymentOrderStatus;
import com.ikram.model.PaymentOrder;
import com.ikram.payload.PaymentLinkResponse;
import com.ikram.payload.dto.BookingDto;
import com.ikram.payload.dto.UserDto;
import com.ikram.repository.PaymentOrderRepository;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IPaymentService implements PaymentService{

    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api-key}")
    private String stripeSecretKey;

    @Value("${razorpay.key-id}")
    private String razorpayApiKey;

    @Value("${razorpay.key-secret}")
    private String razorpayApiSecret;

    @Override
    public PaymentLinkResponse createOrder(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethod) throws RazorpayException {
        Long amount = (long) bookingDto.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(bookingDto.getId());
        order.setSalonId(bookingDto.getSalonId());
        order.setUserId(userDto.getId()); // <= AJOUT ICI AVANT save

        PaymentOrder savedOrder = paymentOrderRepository.save(order); // maintenant userId != null

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            PaymentLink paymentLink = createRazorpayPaymentLink(userDto, savedOrder.getAmount(), savedOrder.getId());
            String paymentUrl = paymentLink.get("short_url");
            String paymentUrlId = paymentLink.get("id");
            paymentLinkResponse.setPaymentLinkUrl(paymentUrl);
            paymentLinkResponse.setPaymentLinkId(paymentUrlId);

            savedOrder.setPaymentLinkId(paymentUrlId);
            paymentOrderRepository.save(savedOrder); // ici, on met à jour juste le lien
        } else {
            String paymentUrl = createStripePaymentLink(userDto, savedOrder.getAmount(), savedOrder.getId());
            paymentLinkResponse.setPaymentLinkUrl(paymentUrl);
        }

        return paymentLinkResponse;
    }


    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);
        if(paymentOrder == null){
            throw new Exception("Payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }


    @Override
    public PaymentLink createRazorpayPaymentLink(UserDto userDto, Long amount, Long orderId) throws RazorpayException {
        Long Amount = amount * 100;
        RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpayApiSecret);
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", Amount);
        paymentLinkRequest.put("currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("name", userDto.getFullName());
        customer.put("email", userDto.getEmail());

        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("email", true);

        paymentLinkRequest.put("notify", notify);

        paymentLinkRequest.put("reminder_enable", true);

        paymentLinkRequest.put("callback_url", "http://localhost:3000/payment-success/" + orderId);

        paymentLinkRequest.put("callback_method", "get");

        return razorpayClient.paymentLink.create(paymentLinkRequest);
    }

    @Override
    public String createStripePaymentLink(UserDto userDto, Long amount, Long orderId) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount * 100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Salon Appointment Booking")
                                        .build())
                                .build())
                        .build())
                .build();

        try {
            Session session = Session.create(params);
            return session.getUrl();
        } catch (Exception e) {
            throw new RuntimeException("Stripe session creation failed", e);
        }
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey,razorpayApiSecret);

                Payment payment = razorpayClient.payments.fetch(paymentId);
                Integer amount = payment.get("amount");
                String status =  payment.get("status");
                if (status.equals("captured")){
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepository.save(paymentOrder);
                    return true;
                    
                }
                return false;
            }
            else {
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                return true;
            }
        }
        return false;
    }
}
