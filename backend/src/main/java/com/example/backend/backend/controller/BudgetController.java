package com.example.backend.backend.controller;

import com.example.backend.backend.dto.ApiResponse;
import com.example.backend.backend.dto.BudgetDTO;
import com.example.backend.backend.model.BudgetModel;
import com.example.backend.backend.services.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public ResponseEntity<ApiResponse> createBudget(@Valid @RequestBody BudgetDTO budgetDTO) {
        BudgetModel budget = budgetService.createBudget(budgetDTO);
        ApiResponse response = new ApiResponse("Presupuesto creado con éxito", budget);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<BudgetModel>>getAllBudgetForAuthUser() {
        List<BudgetModel> budgets = budgetService.getAllBudgetForAuthUser();
        return ResponseEntity.ok(budgets);
    }
}
