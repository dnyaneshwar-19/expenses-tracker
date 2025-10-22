package com.expenses_tracker.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.expenses_tracker.entity.Budget;
import com.expenses_tracker.entity.Expense;
import com.expenses_tracker.entity.User;
import com.expenses_tracker.repository.BudgetRepository;
import com.expenses_tracker.repository.ExpenseRepository;
import com.expenses_tracker.repository.UserRepository;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BudgetController {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    /**
     * Create a new budget
     */
    @PostMapping
    public Budget createBudget(@RequestBody Budget budget) {
        // Validate that user exists
        if (budget.getUser() == null || budget.getUser().getId() == null) {
            throw new RuntimeException("User ID must be provided to create a budget.");
        }

        Long userId = budget.getUser().getId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        budget.setUser(user);
        return budgetRepository.save(budget);
    }

    /**
     * Get all budgets for a specific user
     */
    @GetMapping("/user/{userId}")
    public List<Budget> getBudgetsByUserId(@PathVariable Long userId) {
        return budgetRepository.findByUserId(userId);
    }

    /**
     * Get all budgets
     */
    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    /**
     * Get budget by ID
     */
    @GetMapping("/{id}")
    public Budget getBudgetById(@PathVariable Long id) {
        return budgetRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
    }

    /**
     * Update an existing budget
     */
    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget budgetDetails) {
        Budget existingBudget = budgetRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));

        existingBudget.setCategory(budgetDetails.getCategory());
        existingBudget.setLimitAmount(budgetDetails.getLimitAmount());
        existingBudget.setStartDate(budgetDetails.getStartDate());
        existingBudget.setEndDate(budgetDetails.getEndDate());

        return budgetRepository.save(existingBudget);
    }

    /**
     * Get actual spending for a specific budget
     */
    @GetMapping("/{id}/spending")
    public BigDecimal getBudgetSpending(@PathVariable Long id) {
        Budget budget = budgetRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
        
        // Get all expenses for this category and date range
        List<Expense> expenses = expenseRepository.findByUserId(budget.getUser().getId());
        
        return expenses.stream()
            .filter(e -> e.getCategory().equals(budget.getCategory()))
            .filter(e -> !e.getDate().isBefore(budget.getStartDate()))
            .filter(e -> !e.getDate().isAfter(budget.getEndDate()))
            .map(Expense::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Delete a budget
     */
    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable Long id) {
        Budget budget = budgetRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Budget not found with id: " + id));
        budgetRepository.delete(budget);
    }

    /**
     * Get active budget for a user and category
     */
    @GetMapping("/active/user/{userId}/category/{category}")
    public Optional<Budget> getActiveBudgetByUserAndCategory(@PathVariable Long userId, 
                                                             @PathVariable String category) {
        LocalDate today = LocalDate.now();
        return budgetRepository.findActiveBudgetByUserAndCategory(userId, category, today);
    }

    /**
     * Calculate total spending for a user and category within a date range
     */
    @GetMapping("/spending/user/{userId}/category/{category}")
    public BigDecimal getTotalSpendingByUserAndCategory(@PathVariable Long userId, 
                                                        @PathVariable String category,
                                                        @RequestParam String startDate,
                                                        @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return budgetRepository.calculateTotalSpendingByUserAndCategory(userId, category, start, end);
    }
}
