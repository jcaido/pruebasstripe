package com.jcaido.pruebastripe.ontroller;

import com.itextpdf.text.DocumentException;
import com.jcaido.pruebastripe.payload.PaymentIntentDTO;
import com.jcaido.pruebastripe.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.cancel(id);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @GetMapping("/receipt/{id}/{userName}/{titleProduct}")
    public ResponseEntity<InputStreamResource> getReceiptPdf(
            @PathVariable String id, @PathVariable String userName, @PathVariable String titleProduct) throws StripeException, DocumentException, IOException {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
            ByteArrayInputStream documentPdf = paymentService.generateReceiptPdf(paymentIntent, userName, titleProduct);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=receipt.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(documentPdf));
        } catch (DocumentException | IOException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
     }
}
