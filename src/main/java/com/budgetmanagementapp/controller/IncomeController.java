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
    public ResponseEntity<Income> getIncomeById(@PathVariable Long id) {
        Income income = incomeService.getIncomeById(id);
        if (income == null) {
            return ResponseEntity.notFound().build();
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

    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncomeById(@PathVariable Long id, @RequestBody Income updatedIncome) {
        Income income = incomeService.getIncomeById(id);
        if (income == null) {
            return ResponseEntity.notFound().build();
        }
        income.setAmount(updatedIncome.getAmount());
        income.setCategory(updatedIncome.getCategory());
        income.setDescription(updatedIncome.getDescription());
        income.setName(updatedIncome.getName());
        income.setDate(updatedIncome.getDate());
        return ResponseEntity.ok(income);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncomeById(@PathVariable Long id) {
        incomeService.deleteIncomeById(id);
        return ResponseEntity.noContent().build();
    }
}
