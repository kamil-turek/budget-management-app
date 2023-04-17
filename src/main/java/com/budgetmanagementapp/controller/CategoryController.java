package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ExpenseCategory;
import com.budgetmanagementapp.model.IncomeCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping("/incomes")
    public List<IncomeCategory> getIncomeCategories() {
        return Arrays.asList(IncomeCategory.values());
    }

    @GetMapping("/expenses")
    public List<ExpenseCategory> getExpenseCategories() {
        return Arrays.asList(ExpenseCategory.values());
    }
}
