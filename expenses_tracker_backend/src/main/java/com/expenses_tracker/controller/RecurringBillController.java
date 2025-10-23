package com.expenses_tracker.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenses_tracker.entity.RecurringBill;
import com.expenses_tracker.entity.User;
import com.expenses_tracker.repository.RecurringBillRepository;
import com.expenses_tracker.repository.UserRepository;

@RestController
@RequestMapping("/api/recurring-bills")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RecurringBillController {

    @Autowired
    private RecurringBillRepository recurringBillRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new recurring bill
     */
    @PostMapping
    public RecurringBill createRecurringBill(@RequestBody Map<String, Object> billRequest) {
        // Extract userId from request
        Object userIdObj = billRequest.get("userId");
        if (userIdObj == null) {
            throw new RuntimeException("User ID must be provided to create a recurring bill.");
        }

        Long userId = Long.valueOf(userIdObj.toString());
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        // Create recurring bill from request data
        RecurringBill recurringBill = new RecurringBill();
        recurringBill.setName((String) billRequest.get("billName"));
        recurringBill.setAmount(new BigDecimal(billRequest.get("amount").toString()));
        recurringBill.setCategory((String) billRequest.get("category"));
        recurringBill.setFrequency((String) billRequest.get("frequency"));
        recurringBill.setDayOfMonthDue(Integer.valueOf(billRequest.get("dayOfMonthDue").toString()));
        
        if (billRequest.containsKey("nextDueDate")) {
            recurringBill.setNextDueDate(LocalDate.parse((String) billRequest.get("nextDueDate")));
        }
        if (billRequest.containsKey("description")) {
            recurringBill.setDescription((String) billRequest.get("description"));
        }
        
        // Set reminder settings
        if (billRequest.containsKey("reminderDaysBefore")) {
            recurringBill.setReminderDaysBefore(Integer.valueOf(billRequest.get("reminderDaysBefore").toString()));
        }
        if (billRequest.containsKey("reminderHour")) {
            recurringBill.setReminderHour(Integer.valueOf(billRequest.get("reminderHour").toString()));
        }
        if (billRequest.containsKey("reminderMinute")) {
            recurringBill.setReminderMinute(Integer.valueOf(billRequest.get("reminderMinute").toString()));
        }
        
        recurringBill.setUser(user);
        return recurringBillRepository.save(recurringBill);
    }

    /**
     * Get all recurring bills for a specific user
     */
    @GetMapping("/user/{userId}")
    public List<RecurringBill> getRecurringBillsByUserId(@PathVariable Long userId) {
        return recurringBillRepository.findByUserId(userId);
    }

    /**
     * Get all recurring bills
     */
    @GetMapping
    public List<RecurringBill> getAllRecurringBills() {
        return recurringBillRepository.findAll();
    }

    /**
     * Get recurring bill by ID
     */
    @GetMapping("/{id}")
    public RecurringBill getRecurringBillById(@PathVariable Long id) {
        return recurringBillRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recurring bill not found with id: " + id));
    }

    /**
     * Update an existing recurring bill
     */
    @PutMapping("/{id}")
    public RecurringBill updateRecurringBill(@PathVariable Long id, @RequestBody RecurringBill recurringBillDetails) {
        RecurringBill existingBill = recurringBillRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recurring bill not found with id: " + id));

        existingBill.setName(recurringBillDetails.getName());
        existingBill.setAmount(recurringBillDetails.getAmount());
        existingBill.setCategory(recurringBillDetails.getCategory());
        existingBill.setDayOfMonthDue(recurringBillDetails.getDayOfMonthDue());
        
        // Update nextDueDate if provided
        if (recurringBillDetails.getNextDueDate() != null) {
            existingBill.setNextDueDate(recurringBillDetails.getNextDueDate());
        }
        
        // Update description if provided
        if (recurringBillDetails.getDescription() != null) {
            existingBill.setDescription(recurringBillDetails.getDescription());
        }
        
        // Update frequency if provided
        if (recurringBillDetails.getFrequency() != null) {
            existingBill.setFrequency(recurringBillDetails.getFrequency());
        }
        
        // Update reminder settings
        if (recurringBillDetails.getReminderDaysBefore() != null) {
            existingBill.setReminderDaysBefore(recurringBillDetails.getReminderDaysBefore());
        }
        if (recurringBillDetails.getReminderHour() != null) {
            existingBill.setReminderHour(recurringBillDetails.getReminderHour());
        }
        if (recurringBillDetails.getReminderMinute() != null) {
            existingBill.setReminderMinute(recurringBillDetails.getReminderMinute());
        }

        return recurringBillRepository.save(existingBill);
    }

    /**
     * Delete a recurring bill
     */
    @DeleteMapping("/{id}")
    public void deleteRecurringBill(@PathVariable Long id) {
        RecurringBill recurringBill = recurringBillRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recurring bill not found with id: " + id));
        recurringBillRepository.delete(recurringBill);
    }
}
