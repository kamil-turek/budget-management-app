package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.RecordCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @GetMapping
    public List<RecordCategory> getAllCategories() {
        return Arrays.asList(RecordCategory.values());
    }
}
