package com.jcaido.pruebastripe.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.jcaido.pruebastripe.payload.PaymentIntentDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
                                .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
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

    public ByteArrayInputStream generateReceiptPdf(PaymentIntent paymentIntent) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font fontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
        Paragraph title = new Paragraph("recibo", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("Payment Intent ID: " + paymentIntent.getId()));
        document.add(new Paragraph("Amount: " + paymentIntent.getAmount() / 100 + " EUR"));
        document.add(new Paragraph("Currency: " + paymentIntent.getCurrency().toUpperCase()));
        document.add((new Paragraph("Status: " + paymentIntent.getStatus())));

        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
