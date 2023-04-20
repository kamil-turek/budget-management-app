package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.Expense;
import com.budgetmanagementapp.model.User;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserRepository userRepository;


    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        Expense expense = expenseService.getExpenseById(id);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }
        if (!expense.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        expense.setUser(user.get());
        expenseService.saveExpense(expense);
        return ResponseEntity.ok(expense);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Expense> updateExpenseById(@PathVariable Long id, @RequestBody Expense updatedExpense, Principal principal) {
        String email = principal.getName();
        Expense expense = expenseService.getExpenseById(id);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }
        if (!expense.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (updatedExpense.getAmount() != null) {
            expense.setAmount(updatedExpense.getAmount());
        }
        if (updatedExpense.getCategory() != null) {
            expense.setCategory(updatedExpense.getCategory());
        }
        if (updatedExpense.getDescription() != null) {
            expense.setDescription(updatedExpense.getDescription());
        }
        if (updatedExpense.getName() != null) {
            expense.setName(updatedExpense.getName());
        }
        if (updatedExpense.getDate() != null) {
            expense.setDate(updatedExpense.getDate());
        }
        expenseService.saveExpense(expense);
        return ResponseEntity.ok(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpenseById(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        Expense expense = expenseService.getExpenseById(id);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }
        if (!expense.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        expenseService.deleteExpenseById(id);
        return ResponseEntity.noContent().build();
    }
}
