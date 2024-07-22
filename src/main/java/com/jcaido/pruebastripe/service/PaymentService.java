package com.jcaido.pruebastripe.service;

import com.jcaido.pruebastripe.payload.PaymentIntentDTO;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${stripe.key.secret}")
    String secretKey;

    public PaymentIntent paymentIntent(PaymentIntentDTO paymentIntentDTO) {
        Stripe.apiKey = secretKey;

    }
}
