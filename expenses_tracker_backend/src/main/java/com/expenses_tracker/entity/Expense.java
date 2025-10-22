package com.expenses_tracker.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data; // <-- Important: Add this import

@Data // Handles all getters, setters, toString(), etc. automatically
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;
    
    // --- Fields merged from Riya's code ---
    private String category;
    private LocalDate date;
    private String paymentMethod;
}