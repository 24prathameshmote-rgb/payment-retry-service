package com.pratthamesh.paymentretryservice.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

// represents a payment stored in the database
@Entity
public class Payment 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String paymentReference;
    private String userId;
    private double amount;
    private String failureReason;
    private String status;
    private String country;
    private LocalDateTime createdAt;
    private int retryCount;
    private String paymentMethod;
    
    

    public Payment()
    {
        this.createdAt = LocalDateTime.now();
        this.retryCount = 0;
    }


    public Long getId()
    {
        return id;
    }


    public String getPaymentReference()
    {
        return paymentReference;
    }



    public void setPaymentReference(String paymentReference)
    {
        this.paymentReference = paymentReference;
    }

    public String getUserId()
    {
        return userId;
    }


    public void setUserId(String userId)
    {
        this.userId = userId;
    }




    public double getAmount()
    {
        return amount;
    }



    public void setAmount(double amount)
    {
        this.amount = amount;
    }




    public String getCountry()
    {
        return country;
    }




    public void setCountry(String country)
    {
        this.country = country;
    }




    public String getPaymentMethod()
    {
        return paymentMethod;

    }




    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }



    public String getStatus()
    {
        return status;
    }




    public void setStatus(String status)
    {
        this.status = status;
    }





    public int getRetryCount()
    {
        return retryCount;
    }


    public void setRetryCount(int retryCount)
    {
        this.retryCount = retryCount;
    }


    public String getFailureReason()
    {
        return failureReason;
    }


    public void setFailureReason(String failureReason)
    {
        this.failureReason = failureReason;
    }



    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
}
