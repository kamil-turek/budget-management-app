package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.FinancialRecord;
import com.budgetmanagementapp.model.User;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.FinancialRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/finances")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService financialRecordService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<FinancialRecord>> getAllFinancialRecords(Principal principal) {
        List<FinancialRecord> financialRecords = financialRecordService.getAllFinancialRecords();
        return ResponseEntity.ok(financialRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialRecord> getFinancialRecordById(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        FinancialRecord financialRecord = financialRecordService.getFinancialRecordById(id);
        if (financialRecord == null) {
            return ResponseEntity.notFound().build();
        }
        if (!financialRecord.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(financialRecord);
    }

    @PostMapping
    public ResponseEntity<FinancialRecord> saveFinancialRecord(@RequestBody FinancialRecord financialRecord, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        financialRecord.setUser(user.get());
        financialRecordService.saveFinancialRecord(financialRecord);
        return ResponseEntity.ok(financialRecord);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FinancialRecord> updateFinancialRecord(@PathVariable Long id, @RequestBody FinancialRecord updatedFinancialRecord, Principal principal) {
        String email = principal.getName();
        FinancialRecord financialRecord = financialRecordService.getFinancialRecordById(id);
        if (financialRecord == null) {
            return ResponseEntity.notFound().build();
        }
        if (!financialRecord.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (updatedFinancialRecord.getName() != null) {
            financialRecord.setName(updatedFinancialRecord.getName());
        }
        if (updatedFinancialRecord.getAmount() != null) {
            financialRecord.setAmount(updatedFinancialRecord.getAmount());
        }
        if (updatedFinancialRecord.getCategory() != null) {
            financialRecord.setCategory(updatedFinancialRecord.getCategory());
        }
        if (updatedFinancialRecord.getDescription() != null) {
            financialRecord.setDescription(updatedFinancialRecord.getDescription());
        }
        if (updatedFinancialRecord.getDate() != null) {
            financialRecord.setDate(updatedFinancialRecord.getDate());
        }
        financialRecordService.saveFinancialRecord(financialRecord);
        return ResponseEntity.ok(financialRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinancialRecord(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        FinancialRecord financialRecord = financialRecordService.getFinancialRecordById(id);
        if (financialRecord == null) {
            return ResponseEntity.notFound().build();
        }
        if (!financialRecord.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        financialRecordService.deleteFinancialRecordById(id);
        return ResponseEntity.noContent().build();
    }
}
