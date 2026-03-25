package com.pratthamesh.paymentretryservice.service;
import com.pratthamesh.paymentretryservice.model.Payment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests to make sure payment rules behave correctly
public class PaymentServiceTest 
{
    private final PaymentService paymentService = new PaymentService(null);


    @Test
    public void evaluatePayment_ShouldReturnSuccess_ForValidPayment()
    {
        Payment payment = new Payment();
        payment.setUserId("user1");
        payment.setAmount(100);
        payment.setCountry("Ireland");
        payment.setPaymentMethod("CARD");

        paymentService.evaluatePayment(payment);
        assertEquals("SUCCESS", payment.getStatus());
        assertNull(payment.getFailureReason());
    }



    @Test
    public void evaluatePayment_ShouldFail_ForHighAmount()
    {
        Payment payment = new Payment();
        payment.setAmount(2000);
        payment.setCountry("Ireland");
        payment.setPaymentMethod("CARD");

        paymentService.evaluatePayment(payment);


        assertEquals("FAILED", payment.getStatus());
        assertEquals("Amount exceeds the safe level", payment.getFailureReason());

    }



    @Test
    public void evaluatePayment_ShouldFail_ForRiskyCountry()
    {
        Payment payment = new Payment();
        payment.setAmount(100);
        payment.setCountry("Korea");
        payment.setPaymentMethod("CARD");

        paymentService.evaluatePayment(payment);
        
        assertEquals("FAILED", payment.getStatus());
        assertEquals("Payment has been made from a risky country", payment.getFailureReason());


    }

    

    @Test
    public void evaluatePayment_ShouldFail_ForInvalidMethod()
    {
        Payment payment = new Payment();
        payment.setAmount(100);
        payment.setCountry("Ireland");
        payment.setPaymentMethod("CASH");

        paymentService.evaluatePayment(payment);


        assertEquals("FAILED", payment.getStatus());
        assertEquals("Payment method invalid", payment.getFailureReason());

    }



    @Test
    public void evaluatePayment_ShouldFail_ForHighAmountAndRiskyCountry()
    {
        Payment payment = new Payment();
        payment.setAmount(2000);
        payment.setCountry("Korea");
        payment.setPaymentMethod("CARD");


        paymentService.evaluatePayment(payment);


        assertEquals("FAILED", payment.getStatus());
        assertEquals("High amount from a risky country", payment.getFailureReason());

    }



    @Test
    public void evaluatePayment_ShouldHandleNullCountry()
    {
        Payment payment = new Payment();
        payment.setAmount(100);
        payment.setCountry(null);
        payment.setPaymentMethod("CARD");



        paymentService.evaluatePayment(payment);
        assertEquals("SUCCESS", payment.getStatus());
        assertNull(payment.getFailureReason());

    }


    @Test
    public void evaluatePayment_ShouldHandleNullPaymentMethod()
    {
        Payment payment = new Payment();
        payment.setAmount(100);
        payment.setCountry("Ireland");
        payment.setPaymentMethod(null);
        
        

        paymentService.evaluatePayment(payment);




        assertEquals("SUCCESS", payment.getStatus());
        assertNull(payment.getFailureReason());
    }
}
