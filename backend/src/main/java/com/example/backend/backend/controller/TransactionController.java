package com.example.backend.backend.controller;

import com.example.backend.backend.dto.ApiResponse;
import com.example.backend.backend.dto.TransactionsDTO;
import com.example.backend.backend.model.TransactionsModel;
import com.example.backend.backend.services.AccountService;
import com.example.backend.backend.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    //Obtener todas las transacciones
    @GetMapping
    public ResponseEntity<ApiResponse> getAllTransactions()
    {
       List<TransactionsModel> transactions = transactionService.getAllTransaction();
        ApiResponse response = new ApiResponse("Datos de todas las transacciones obtenidas correctamente",transactions);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Crear una transaccion
    @PostMapping
    public ResponseEntity<ApiResponse> createTransaction(@Valid @RequestBody TransactionsDTO transactionsDTO)
    {
        TransactionsModel transiction = transactionService.createTransaction(transactionsDTO);
        ApiResponse response = new ApiResponse("Transacción creada correctamente",transiction);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/by-date")
    public ResponseEntity<ApiResponse>  getTransactionsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate)
    {
             List<TransactionsModel> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
             ApiResponse response = new ApiResponse("Filtro de transacciones por fecha",transactions);
             return new ResponseEntity<>(response,HttpStatus.OK);
    }



    @GetMapping("/by-user/{userId}")
    public ResponseEntity<ApiResponse> getTransactionsByUserId(@PathVariable String userId) {

        List<TransactionsModel> transactions = transactionService.getTransactionsByUserId(userId);
        ApiResponse response = new ApiResponse("Todas las transacciones asociadas al usuario devueltas correctamente",transactions);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("/user/auth")
    public ResponseEntity<List<TransactionsModel>> getAllTransactionsForAuthenticatedUser() {
        List<TransactionsModel> transactions = transactionService.getAllTransactionsForAuthenticatedUser();
        return ResponseEntity.ok(transactions);
    }

}