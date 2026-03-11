package com.codewithmosh.store.payments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutResponse {
    private Long OrderId;
    private String checkoutUrl;
}
