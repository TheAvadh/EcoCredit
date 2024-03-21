package com.group1.ecocredit.services;

import com.group1.ecocredit.dto.ChargeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


public interface StripeService {

    void init();

    Product getProductOfCertainAmount(Long amount) throws StripeException;

    String charge(Long price, Long pickupId) throws StripeException;

    String cancel(String sessionId) throws StripeException;
    boolean paymentCompleted(String sessionId) throws StripeException;


}
