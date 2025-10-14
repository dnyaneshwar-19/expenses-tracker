package com.expenses_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenses_tracker.entity.Expense;
import com.expenses_tracker.repository.ExpenseRepository;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000") // 🔗 Allows requests from your React app
public class ExpenseController {

    @Autowired // Spring automatically injects an instance of ExpenseRepository
    private ExpenseRepository expenseRepository;

    // GET request to /api/expenses
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll(); // Fetches all expenses from the database
    }

    // POST request to /api/expenses
    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseRepository.save(expense); // Saves the new expense to the database
    }
}