package com.pratthamesh.paymentretryservice.controller;
import com.pratthamesh.paymentretryservice.model.Payment;
import com.pratthamesh.paymentretryservice.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


// Controller handels incoming requests and connects them to the service layer
@Controller
public class PaymentController
{
    private final PaymentService paymentService;
    // Injecting the service so we can use the business logic here
    public PaymentController(PaymentService paymentService)
    {
        this.paymentService = paymentService;
    }
    // Loads all payments and sends them to the frontend
    @GetMapping("/payments")
    public String showAllPayments(Model model)
    {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "payments";
    }
    // Opens the form to create a new payment
    @GetMapping("/payments/new")
    public String showPaymentForm(Model model)
    {
        model.addAttribute("payment", new Payment());
        return "payment-form";

    }
    // Takes form input and creates a payment
    @PostMapping("/payments")
    public String createPayment(@ModelAttribute Payment payment)
    {
        paymentService.createPayment(payment);
        return "redirect:/payments";
    }
    // Retries a payment if it failed earlier
    @PostMapping("/payments/{id}/retry")
    public String retryPayment(@PathVariable Long id)
    {
        paymentService.retryPayment(id);
        return "redirect:/payments";
    }

}
