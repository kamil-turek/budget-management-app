package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.FinancialRecord;
import com.budgetmanagementapp.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImplementation implements FinancialRecordService {

    private final FinancialRecordRepository financialRecordRepository;

    @Override
    public List<FinancialRecord> getAllFinancialRecords() {
        return financialRecordRepository.findAll();
    }

    @Override
    public FinancialRecord saveFinancialRecord(FinancialRecord financialRecord) {
        return financialRecordRepository.save(financialRecord);
    }

    @Override
    public FinancialRecord getFinancialRecordById(Long id) {
        return financialRecordRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFinancialRecordById(Long id) {
        financialRecordRepository.deleteById(id);
    }
}
