package com.expenses_tracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expenses_tracker.entity.Expense;
import com.expenses_tracker.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000") // Allows your frontend to call this backend
public class ExpenseController {

    // The Controller only talks to the Service layer
    @Autowired
    private ExpenseService expenseService;

    // POST: Add a new expense
    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    // GET: Get all expenses
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }
    
    // PUT: Update an existing expense by its ID
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    // DELETE: Delete an expense by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok().build(); // Return a simple 200 OK success response
    }

    // GET: Search expenses by a keyword in description or category
    @GetMapping("/search")
    public List<Expense> searchExpenses(@RequestParam String keyword) {
        return expenseService.searchByKeyword(keyword);
    }
    
    // GET: Filter expenses by category
    @GetMapping("/filter/category")
    public List<Expense> filterByCategory(@RequestParam String category) {
        return expenseService.filterByCategory(category);
    }

    // GET: Filter expenses by payment method
    @GetMapping("/filter/payment-method")
    public List<Expense> filterByPaymentMethod(@RequestParam String paymentMethod) {
        return expenseService.filterByPaymentMethod(paymentMethod);
    }

    // GET: Filter expenses by a date range
    @GetMapping("/filter/date-range")
    public List<Expense> filterByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return expenseService.filterByDateRange(startDate, endDate);
    }
}