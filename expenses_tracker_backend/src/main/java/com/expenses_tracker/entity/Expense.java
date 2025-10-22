package com.expenses_tracker.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Added title field
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private String paymentMethod;
    private boolean isPinned = false;
    private String expenseType; // "PERSONAL" or "PROFESSIONAL"
    
    // Category is now a plain String, no JPA relationship
    private String category;

    // --- Relationships ---
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "user_id", nullable = false)
   @JsonIgnore
   private User user;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "group_id")
   @JsonIgnore
   private Group group;

   // Split details for group expenses (userId -> amount)
   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name = "expense_splits", joinColumns = @JoinColumn(name = "expense_id"))
   @MapKeyColumn(name = "user_id")
   @Column(name = "amount")
   @JsonIgnore
   private Map<Long, BigDecimal> splitDetails = new HashMap<>();

    // --- Getters and Setters ---
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Map<Long, BigDecimal> getSplitDetails() {
        return splitDetails;
    }

    public void setSplitDetails(Map<Long, BigDecimal> splitDetails) {
        this.splitDetails = splitDetails;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
