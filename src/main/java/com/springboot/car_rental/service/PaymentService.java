package com.springboot.car_rental.service;

import com.springboot.car_rental.entity.Payment;
import com.springboot.car_rental.entity.BookACar;
import com.springboot.car_rental.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;

import com.springboot.car_rental.repository.BookACarRepository;
import com.springboot.car_rental.dto.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@Service
public class PaymentService {

	@Autowired
    private BookACarRepository bookACarRepository;
	
	@Autowired
    private PaymentRepository paymentRepository;
	
	@Value("${stripe.api.key}")
    private String stripeSecretKey;

	@PostConstruct
	public void initializeStripe() {
	    Stripe.apiKey = stripeSecretKey; // Initialisation Stripe
	}
	
	public Payment savePayment(Payment payment) {
        // Enregistrer le paiement dans la base de données
        return paymentRepository.save(payment);
    }

	

    public InvoiceDto getInvoice(Long bookingId) {
        BookACar booking = bookACarRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        booking.calculatePrice(); // Assurez-vous que le prix est calculé
        return booking.generateInvoice();
    }
    
}
