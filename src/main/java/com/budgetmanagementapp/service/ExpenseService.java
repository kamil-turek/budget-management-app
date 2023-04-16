package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.Expense;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ExpenseService {
    List<Expense> getAllExpenses();

    Expense saveExpense(Expense expense);

    Expense getExpenseById(Long id);

    void deleteExpenseById(Long id);
}
