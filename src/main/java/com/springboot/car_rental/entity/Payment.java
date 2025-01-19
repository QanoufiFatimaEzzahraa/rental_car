package com.springboot.car_rental.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    private double amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookACar bookACar; 

    // Statut du paiement (ex: "Processed", "Pending", "Failed")

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date paymentDate; 
    // Date et heure du paiement
    private Long userid;
    private Long carid;


    // Méthode pour récupérer le montant depuis BookACar
    public double getAmount() {
        return bookACar.calculatePrice();
    }


		
	
}
