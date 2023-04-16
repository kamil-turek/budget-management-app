package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.Income;
import com.budgetmanagementapp.repository.ExpenseRepository;
import com.budgetmanagementapp.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IncomeServiceImplementation implements IncomeService {
    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeServiceImplementation(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    @Override
    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public Income getIncomeById(Long id) {
        return incomeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteIncomeById(Long id) {
        incomeRepository.deleteById(id);
    }
}
