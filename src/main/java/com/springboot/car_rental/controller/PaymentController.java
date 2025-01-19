package com.springboot.car_rental.controller;

import com.springboot.car_rental.entity.BookACar;
import com.springboot.car_rental.entity.Payment;
import com.springboot.car_rental.entity.User;
import com.springboot.car_rental.service.PaymentService;
import com.springboot.car_rental.repository.BookACarRepository;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import lombok.RequiredArgsConstructor;

import com.springboot.car_rental.service.AdminBookACarService;
import com.springboot.car_rental.dto.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

	@Autowired
    private PaymentService paymentService;
		
	@Autowired
	private BookACarRepository bookACarRepository;
	
	
	@PostMapping("/process")
    public Payment processPayment(@RequestParam Long bookingId) {
        // Récupérer la réservation de voiture par son ID
        BookACar booking = bookACarRepository.getById(bookingId);
        if (booking == null) {
            throw new RuntimeException("Réservation non trouvée");
        }

        // Créer un objet Payment
        Payment payment = new Payment();
        payment.setBookACar(booking);
        payment.setPaymentDate(new Date()); // Date actuelle du paiement
        payment.setAmount(booking.calculatePrice()); 
        payment.setUserid(booking.getUser().getId()); 
        payment.setCarid(booking.getCar().getId()); 

        // Montant calculé de la réservation

        // Enregistrer le paiement dans la base de données
        return paymentService.savePayment(payment);
    }
	
	
    
    

    // Endpoint pour récupérer la facture d'une réservation
    @GetMapping("/invoice/{bookingId}")
    public ResponseEntity<InvoiceDto> getInvoice(@PathVariable Long bookingId) {
        InvoiceDto invoice = paymentService.getInvoice(bookingId);
        return ResponseEntity.ok(invoice);
    }
}
