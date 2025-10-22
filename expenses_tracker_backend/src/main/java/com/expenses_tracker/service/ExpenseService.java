package com.expenses_tracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenses_tracker.entity.Expense;
import com.expenses_tracker.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    // === Basic CRUD Operations ===

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        return expenseRepository.findById(id)
                .map(expense -> {
                    expense.setDescription(updatedExpense.getDescription());
                    expense.setAmount(updatedExpense.getAmount());
                    expense.setCategory(updatedExpense.getCategory());
                    expense.setDate(updatedExpense.getDate());
                    // IMPORTANT: Added paymentMethod to the update logic
                    expense.setPaymentMethod(updatedExpense.getPaymentMethod()); 
                    return expenseRepository.save(expense);
                })
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }

    // === Search and Filter Operations ===

    public List<Expense> searchByKeyword(String keyword) {
        return expenseRepository.searchExpenses(keyword);
    }

    public List<Expense> filterByCategory(String category) {
        return expenseRepository.findByCategoryIgnoreCase(category);
    }
    
    public List<Expense> filterByPaymentMethod(String paymentMethod) {
        return expenseRepository.findByPaymentMethodIgnoreCase(paymentMethod);
    }

    public List<Expense> filterByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDateBetween(startDate, endDate);
    }
}