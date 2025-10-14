package com.expenses_tracker.repository;

import com.expenses_tracker.entity.Expense; // <-- This import line is required
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Spring Data JPA provides all CRUD methods like findAll(), findById(), save()
    // You can also add custom query methods here if needed.
}