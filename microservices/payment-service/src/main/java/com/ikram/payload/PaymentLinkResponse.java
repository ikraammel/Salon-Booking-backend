package com.ikram.payload;

import lombok.Data;

@Data
public class PaymentLinkResponse {

    private String paymentLinkUrl;
    public String paymentLinkId;
}
