package com.jcaido.pruebastripe.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentIntentDTO {
    public enum Currency{
        USD, EUR;
    }
    private String description;
    private int amount;
    private Currency currency;
}
