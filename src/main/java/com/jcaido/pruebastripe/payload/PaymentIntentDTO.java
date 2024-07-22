package com.jcaido.pruebastripe.payload;

public class PaymentIntentDTO {
    public enum Currency{
        USD, EUR;
    }
    private String description;
    private int amount;
    private Currency currency;
}
