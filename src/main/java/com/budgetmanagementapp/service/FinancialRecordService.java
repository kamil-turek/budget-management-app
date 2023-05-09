package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.FinancialRecord;

import java.util.List;

public interface FinancialRecordService {
    List<FinancialRecord> getAllFinancialRecords();

    FinancialRecord saveFinancialRecord(FinancialRecord financialRecord);

    FinancialRecord getFinancialRecordById(Long id);

    void deleteFinancialRecordById(Long id);
}
