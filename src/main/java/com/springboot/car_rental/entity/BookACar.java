package com.springboot.car_rental.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.car_rental.dto.BookACarDto;
import com.springboot.car_rental.dto.InvoiceDto;
import com.springboot.car_rental.enums.BookCarStatus;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class BookACar {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private Date fromDate;
	    private Date toDate;
	    private Long days;
	    private double pricePerDay;
	    private double price;
	    
	    private BookCarStatus bookCarStatus;
	    
	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "user_id", nullable = false)
	    @OnDelete(action = OnDeleteAction.CASCADE)
	    @JsonIgnore // @JsonIgnore is used to ignore the user field when serializing the object to JSON.
	    private User user;
	    
	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "car_id", nullable = false)
	    @OnDelete(action = OnDeleteAction.CASCADE)
	    @JsonIgnore
	    private Car car;
	    
	 // Méthode pour calculer le prix total
	    public double calculatePrice() {
	        this.price = this.days * this.pricePerDay;
	        return this.price;
	    }
	    
	    public BookACarDto getBookACarDto() {
	        BookACarDto bookACarDto = new BookACarDto();
	        bookACarDto.setId(this.id);
	        bookACarDto.setDays(this.days);
	        bookACarDto.setBookCarStatus(this.bookCarStatus);
	        bookACarDto.setPricePerDay(this.pricePerDay);
	        bookACarDto.setPrice(this.price);
	        bookACarDto.setToDate(this.toDate);
	        bookACarDto.setFromDate(this.fromDate);
	        bookACarDto.setEmail(this.user.getEmail());
	        bookACarDto.setUsername(this.user.getFirstName()+" "+this.user.getLastName());
	        bookACarDto.setUserId(this.user.getId());
	        bookACarDto.setCarId(this.car.getId());
	        return bookACarDto;
	    }
	    
	 // Méthode pour générer une facture
	    public InvoiceDto generateInvoice() {
	        InvoiceDto invoice = new InvoiceDto();
	        invoice.setBookingId(this.id);
	        invoice.setFromDate(this.fromDate);
	        invoice.setToDate(this.toDate);
	        invoice.setDays(this.days);
	        invoice.setPricePerDay(this.pricePerDay);
	        invoice.setTotalPrice(this.price);
	        invoice.setUserName(this.user.getFirstName() + " " + this.user.getLastName());
	        invoice.setUserEmail(this.user.getEmail());
	        invoice.setCarDetails(this.car.getBrand() + " " + this.car.getModel());
	        return invoice;
	    }

}
