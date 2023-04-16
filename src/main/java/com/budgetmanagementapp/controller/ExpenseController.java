package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.Expense;
import com.budgetmanagementapp.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense) {
        expenseService.saveExpense(expense);
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpenseById(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setDescription(updatedExpense.getDescription());
        expense.setName(updatedExpense.getName());
        expense.setDate(updatedExpense.getDate());
        return ResponseEntity.ok(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpenseById(@PathVariable Long id) {
        expenseService.deleteExpenseById(id);
        return ResponseEntity.noContent().build();
    }
}
