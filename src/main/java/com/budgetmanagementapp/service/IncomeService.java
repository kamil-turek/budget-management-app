package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.Income;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IncomeService {
    List<Income> getAllIncomes();

    Income saveIncome(Income income);

    Income getIncomeById(Long id);

    void deleteIncomeById(Long id);
}
