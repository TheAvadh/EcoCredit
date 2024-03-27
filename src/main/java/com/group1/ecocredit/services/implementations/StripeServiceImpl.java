package com.group1.ecocredit.services.implementations;

import com.group1.ecocredit.services.PickupPaymentActionService;
import com.group1.ecocredit.services.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.ProductListParams;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.api.secret.key}")
    String stripeSecretKey;

    @Value("${base.url.frontend}")
    private String baseUrlFrontend;

    private static String PAID = "paid";


    @Autowired
    PickupPaymentActionService pickupPaymentActionService;

    @Override
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }
    

    public Product getProductOfCertainAmount(Long amount) throws StripeException {

        ProductListParams params = ProductListParams.builder().setActive(true).build();

        List<Product> shippingProducts = Product.list(params).getData();


        shippingProducts.removeIf(product -> Integer.parseInt(product.getMetadata().get("amount")) != amount);

        return shippingProducts.getFirst();
    }

    public String charge(Long price, Long pickupId) throws StripeException {

        Product product = getProductOfCertainAmount(price);

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPrice(product.getDefaultPrice())
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(baseUrlFrontend + "/success?pickupId=" + pickupId)
                .setCancelUrl(baseUrlFrontend + "/failure")
                .addLineItem(lineItem).build();

        Session session = Session.create(params);

        pickupPaymentActionService.addSessionIdToPayment(pickupId, session.getId());

        return session.getUrl();
    }

    @Override
    public boolean paymentCompleted(String sessionId) throws StripeException {
        Session session = Session.retrieve(sessionId);
        return PAID.equals(session.getPaymentStatus());
    }

    @Override
    public String cancel(String sessionId) throws StripeException {

        Session session = Session.retrieve(sessionId);
        String paymentId = session.getPaymentIntent();

        RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(paymentId).build();

        Refund refund = Refund.create(params);

        return refund.getStatus();
    }

}
