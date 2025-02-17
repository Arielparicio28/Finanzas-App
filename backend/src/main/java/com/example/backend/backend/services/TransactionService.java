package com.example.backend.backend.services;

import com.example.backend.backend.dto.TransactionsDTO;
import com.example.backend.backend.enums.TransactionType;
import com.example.backend.backend.model.AccountModel;
import com.example.backend.backend.model.TransactionsModel;
import com.example.backend.backend.repository.AccountRepository;
import com.example.backend.backend.repository.TransactionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    // Obtener todas las transacciones
    public List<TransactionsModel> getAllTransaction() {
        try {
            return transactionRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener datos", e
            );
        }
    }

    //Crear una transaccion.
    public TransactionsModel createTransaction(TransactionsDTO transactionsDTO)
    {
        AccountModel account = accountRepository.findById(new ObjectId(transactionsDTO.getAccountId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
        try
        {
            // Crear la transacción
            TransactionsModel transaction = new TransactionsModel();
            transaction.setAccountId(account.getId());
            transaction.setAmount(transactionsDTO.getAmount());
            transaction.setType(transactionsDTO.getType());
            transaction.setCategory(transactionsDTO.getCategory());
            transaction.setDateOfTransaction(new Date());
            transaction.setDescription(transactionsDTO.getDescription());

            // Guardar la transacción
            TransactionsModel savedTransaction = transactionRepository.save(transaction);

            // Actualizar el balance de la cuenta según el tipo de transacción
            double newBalance;
            if (transaction.getType() == TransactionType.CREDITO) {
                newBalance = account.getBalance() + transaction.getAmount();
            } else if (transaction.getType() == TransactionType.DEBITO) {
                newBalance = account.getBalance() - transaction.getAmount();
                if (newBalance < 0) {
                    // Lanza la excepción de saldo insuficiente sin capturarla en el catch genérico
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente para realizar la transacción");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de transacción inválido");
            }

            account.setBalance(newBalance);
            account.setUpdatedAt(new Date());
            accountRepository.save(account);

            return savedTransaction;
        } catch (ResponseStatusException e) {
            // Re-lanza directamente las ResponseStatusException para que se propague el error correcto
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la transacción", e);
        }
        }


    //Buscar transaccion por fecha
    public List<TransactionsModel> getTransactionsByDateRange(Date startDate, Date endDate) {
        try {
            return transactionRepository.findTransactionsByDateRange(startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


















    }

