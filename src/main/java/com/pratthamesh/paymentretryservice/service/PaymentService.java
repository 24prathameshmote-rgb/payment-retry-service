package com.pratthamesh.paymentretryservice.service;
import com.pratthamesh.paymentretryservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import com.pratthamesh.paymentretryservice.model.Payment;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService 
{
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository)
    {
        this.paymentRepository = paymentRepository;
    }

    // Created a payment and decides if it should pass or fail
    public Payment createPayment(Payment payment)
    {
        // give each payment a unique reference
        payment.setPaymentReference(generatePaymentReference());
        // start with no retries
        payment.setRetryCount(0);
        // check rules (amount, country, method)
        evaluatePayment(payment);
        return paymentRepository.save(payment);
    }






    public List<Payment> getAllPayments()
    {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id)
    {
        return paymentRepository.findById(id).orElse(null);
    }




    // tries the payment again if it previously failed
    public Payment retryPayment(Long id)
    {
        Payment payment = getPaymentById(id);
        // if we didn't find the payment, nothing to do
        if (payment == null)
        {
            return null;
        }

        // don't retry if it's already done or permanently failed
        if ("SUCCESS".equals(payment.getStatus()) || "PERMANENTLY_FAILED".equals(payment.getStatus()))
        {
            return payment;
        }
        // increase retry count
        int newRetryCount = payment.getRetryCount() + 1;
        payment.setRetryCount(newRetryCount);
        // run the same checks again
        evaluatePayment(payment);

        // after 3 tries, we stop retrying completely
        if ("FAILED".equals(payment.getStatus()) && payment.getRetryCount() >= 3)
        {
            payment.setStatus("PERMANENTLY_FAILED");
            payment.setFailureReason("Maximum retry attempts reached");
        }



        return paymentRepository.save(payment);
    }


    // This is the main logic that will decide if the payment succeeds or fails
    public void evaluatePayment(Payment payment)
    {
        boolean highAmount = payment.getAmount() > 1500;
        boolean riskyCountry = isRiskyCountry(payment.getCountry());
        boolean invalidMethod = isInvalidPaymentMethod(payment.getPaymentMethod());
        // check combinations first
        if (highAmount && riskyCountry)
        {
            payment.setStatus("FAILED");
            payment.setFailureReason("High amount from a risky country");


        }

        else if (highAmount)
        {
            payment.setStatus("FAILED");
            payment.setFailureReason("Amount exceeds the safe level");
        }

        else if (riskyCountry)
        {
            payment.setStatus("FAILED");
            payment.setFailureReason("Payment has been made from a risky country");
        }

        else if (invalidMethod)
        {
            payment.setStatus("FAILED");
            payment.setFailureReason("Payment method invalid");
        }



        else 
        {
            // if none of the above fail, it is successful
            payment.setStatus("SUCCESS");
            payment.setFailureReason(null);
        }
    }

    //  check for risky countries
    private boolean isRiskyCountry(String country)
    {
        if (country == null)
        {
            return false;
        }


        return country.equalsIgnoreCase("Korea") || country.equalsIgnoreCase("Kuwait") || country.equalsIgnoreCase("Burma");
    }

    // only allow specific payment methods
    private boolean isInvalidPaymentMethod(String paymentMethod)
    {
        if (paymentMethod == null)
        {
            return false;
        }

        return !(paymentMethod.equalsIgnoreCase("BANK_TRANSFER") || paymentMethod.equalsIgnoreCase("DIGITAL_WALLET") || paymentMethod.equalsIgnoreCase("CARD"));
    }

    // generates a short unique reference for each payment
    private String generatePaymentReference()
    {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }


}
