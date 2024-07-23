package com.jcaido.pruebastripe.ontroller;

import com.jcaido.pruebastripe.payload.PaymentIntentDTO;
import com.jcaido.pruebastripe.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/paymentintent")
    public ResponseEntity<String> payment(@RequestBody PaymentIntentDTO paymentIntentDTO) throws StripeException {
        PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDTO);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirm(@PathVariable String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.confirm(id);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }
}
