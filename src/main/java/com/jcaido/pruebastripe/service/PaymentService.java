package com.jcaido.pruebastripe.service;

import com.jcaido.pruebastripe.payload.PaymentIntentDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${stripe.key.secret}")
    String secretKey;

    public PaymentIntent paymentIntent(PaymentIntentDTO paymentIntentDTO) throws StripeException {
        Stripe.apiKey = secretKey;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(paymentIntentDTO.getAmount())
                .setCurrency(paymentIntentDTO.getCurrency().toString())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        return PaymentIntent.create(params);
    }

    public PaymentIntent confirm(String id) throws StripeException {
        Stripe.apiKey = secretKey;

        PaymentIntent resource = PaymentIntent.retrieve(id);

        PaymentIntentConfirmParams params = PaymentIntentConfirmParams.builder()
                .setPaymentMethod("pm_card_visa")
                .build();

        return resource.confirm(params);
    }

    public PaymentIntent cancel(String id) throws StripeException {
        Stripe.apiKey = secretKey;

        PaymentIntent resource = PaymentIntent.retrieve(id);
        PaymentIntentCancelParams params = PaymentIntentCancelParams.builder().build();

        return resource.cancel(params);
    }
}
