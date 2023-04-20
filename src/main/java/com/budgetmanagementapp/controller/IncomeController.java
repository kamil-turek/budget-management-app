package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.Income;
import com.budgetmanagementapp.model.User;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> incomes = incomeService.getAllIncomes();
        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        Income income = incomeService.getIncomeById(id);
        if (income == null) {
            return ResponseEntity.notFound().build();
        }
        if (!income.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(income);
    }

    @PostMapping
    public ResponseEntity<Income> saveIncome(@RequestBody Income income, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        income.setUser(user.get());
        incomeService.saveIncome(income);
        return ResponseEntity.ok(income);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Income> updateIncomeById(@PathVariable Long id, @RequestBody Income updatedIncome, Principal principal) {
        String email = principal.getName();
        Income income = incomeService.getIncomeById(id);
        if (income == null) {
            return ResponseEntity.notFound().build();
        }
        if (!income.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (updatedIncome.getAmount() != null) {
            income.setAmount(updatedIncome.getAmount());
        }
        if (updatedIncome.getCategory() != null) {
            income.setCategory(updatedIncome.getCategory());
        }
        if (updatedIncome.getDescription() != null) {
            income.setDescription(updatedIncome.getDescription());
        }
        if (updatedIncome.getName() != null) {
            income.setName(updatedIncome.getName());
        }
        if (updatedIncome.getDate() != null) {
            income.setDate(updatedIncome.getDate());
        }
        incomeService.saveIncome(income);
        return ResponseEntity.ok(income);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncomeById(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        Income income = incomeService.getIncomeById(id);
        if (income == null) {
            return ResponseEntity.notFound().build();
        }
        if (!income.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        incomeService.deleteIncomeById(id);
        return ResponseEntity.noContent().build();
    }
}
