package com.expenses_tracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenses_tracker.entity.Budget;
import com.expenses_tracker.entity.Expense;
import com.expenses_tracker.entity.User; // <-- 1. NEW IMPORT
import com.expenses_tracker.repository.BudgetRepository;
import com.expenses_tracker.repository.ExpenseRepository;
import com.expenses_tracker.repository.UserRepository; // <-- 2. NEW IMPORT

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository; // <-- 3. INJECT USER REPOSITORY

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Expense addExpense(Expense expense) {
        // Validate that user was set (should be set by controller from authentication)
        if (expense.getUser() == null) {
            throw new RuntimeException("User must be set to add an expense.");
        }

        // If user has only ID, fetch the managed entity
        if (expense.getUser().getId() != null && expense.getUser().getUsername() == null) {
            Long userId = expense.getUser().getId();
            User managedUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            expense.setUser(managedUser);
        }

        // Set default date if null
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
        
        // Save the expense
        Expense savedExpense = expenseRepository.save(expense);
        
        // Check for budget alerts after saving the expense
        checkBudgetAlerts(savedExpense);
        
        return savedExpense;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    @Override
    public List<Expense> getExpensesByType(String expenseType) {
        return expenseRepository.findByExpenseTypeIgnoreCase(expenseType);
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }

    @Override
    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense existing = getExpenseById(id);

        // Update allowed fields (null checks optional)
        existing.setTitle(expenseDetails.getTitle());
        existing.setDescription(expenseDetails.getDescription());
        existing.setAmount(expenseDetails.getAmount());
        existing.setDate(expenseDetails.getDate());
        existing.setCategory(expenseDetails.getCategory());
        existing.setPaymentMethod(expenseDetails.getPaymentMethod());
        existing.setExpenseType(expenseDetails.getExpenseType());
        existing.setPinned(expenseDetails.isPinned());
        
        // Note: You probably don't want to update the user on an existing expense
        // so we don't set user here.

        return expenseRepository.save(existing);
    }

    @Override
    public void deleteExpense(Long id) {
        Expense e = getExpenseById(id);
        expenseRepository.delete(e);
    }

    @Override
    public void togglePin(Long id) {
        Expense e = getExpenseById(id);
        e.setPinned(!e.isPinned());
        expenseRepository.save(e);
    }

    @Override
    public List<Expense> searchByKeyword(String keyword) {
        // searches title OR description OR category name (case-insensitive contains)
        return expenseRepository.searchExpenses(keyword);

    }

    @Override
    public List<Expense> filterByCategory(String category) {
        return expenseRepository.findByCategoryIgnoreCase(category);
    }

    @Override
    public List<Expense> filterByPaymentMethod(String paymentMethod) {
        return expenseRepository.findByPaymentMethodIgnoreCase(paymentMethod);
    }

    @Override
    public List<Expense> filterByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public List<Expense> filterByTypeAndCategory(String expenseType, String category) {
        return expenseRepository.findByExpenseTypeIgnoreCaseAndCategoryIgnoreCase(expenseType, category);
    }

    /**
     * Check for budget alerts after adding a new expense
     */
    private void checkBudgetAlerts(Expense expense) {
        try {
            Long userId = expense.getUser().getId();
            String category = expense.getCategory();
            LocalDate expenseDate = expense.getDate();

            // Find active budget for this user and category
            Optional<Budget> activeBudget = budgetRepository.findActiveBudgetByUserAndCategory(
                userId, category, expenseDate);

            if (activeBudget.isPresent()) {
                Budget budget = activeBudget.get();
                
                // Calculate total spending for this category in the budget's date range
                BigDecimal totalSpending = budgetRepository.calculateTotalSpendingByUserAndCategory(
                    userId, category, budget.getStartDate(), budget.getEndDate());

                // Check if spending exceeds 90% of budget limit
                BigDecimal threshold = budget.getLimitAmount().multiply(new BigDecimal("0.9"));
                
                if (totalSpending.compareTo(threshold) > 0) {
                    // Calculate remaining budget
                    BigDecimal remainingBudget = budget.getLimitAmount().subtract(totalSpending);
                    
                    String message;
                    if (remainingBudget.compareTo(BigDecimal.ZERO) <= 0) {
                        message = String.format("Budget Alert: You have exceeded your %s budget of ₹%.2f!", 
                            category, budget.getLimitAmount());
                    } else {
                        message = String.format("Budget Alert: You have only ₹%.2f left in your %s budget!", 
                            remainingBudget, category);
                    }
                    
                    // Create notification for the user
                    notificationService.createNotification(userId, message);
                }
            }
        } catch (Exception e) {
            // Log the error but don't fail the expense creation
            System.err.println("Error checking budget alerts: " + e.getMessage());
        }
    }
}