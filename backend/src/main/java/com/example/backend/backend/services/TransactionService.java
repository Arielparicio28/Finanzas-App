package com.example.backend.backend.services;

import com.example.backend.backend.dto.TransactionsDTO;
import com.example.backend.backend.enums.TransactionType;
import com.example.backend.backend.model.AccountModel;
import com.example.backend.backend.model.TransactionsModel;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.repository.AccountRepository;
import com.example.backend.backend.repository.TransactionRepository;
import com.example.backend.backend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
 import java.util.Calendar;

@Service
public class TransactionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    // Obtener todas las transacciones
    public List<TransactionsModel> getAllTransaction() {
        try {
            List<TransactionsModel> transactions = transactionRepository.findAll();
            if (transactions.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay transacciones registradas");
            }
            return transactions;
        }

        catch(ResponseStatusException e)
        {
            throw e;
        }

        catch (Exception e) {
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
            double newBalance;

            if (transactionsDTO.getAmount() == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El amount no puede ser 0");
            }

            // Validar saldo antes de guardar la transacción
            if (transactionsDTO.getType() == TransactionType.CREDITO) {
                newBalance = account.getBalance() + transactionsDTO.getAmount();
            } else if (transactionsDTO.getType() == TransactionType.DEBITO) {
                newBalance = account.getBalance() - transactionsDTO.getAmount();

                if (newBalance < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente para realizar la transacción");
                }

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de transacción inválido");
            }

            // Se crea la transacción
            TransactionsModel transaction = new TransactionsModel();
            transaction.setAccountId(account.getId());
            transaction.setAmount(transactionsDTO.getAmount());
            transaction.setType(transactionsDTO.getType());
            transaction.setCategory(transactionsDTO.getCategory());
            transaction.setDateOfTransaction(new Date());
            transaction.setDescription(transactionsDTO.getDescription());

            // Guardar la transacción en la base de datos
            TransactionsModel savedTransaction = transactionRepository.save(transaction);

            // Actualizar saldo de la cuenta
            account.setBalance(newBalance);
            account.setUpdatedAt(new Date());
            accountRepository.save(account);

            return savedTransaction;
        } catch (ResponseStatusException e) {
            throw e; // Propagar el error si ya es una ResponseStatusException
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la transacción", e);
        }
        }


    //Buscar transaccion por fecha
    public List<TransactionsModel> getTransactionsByDateRange(Date startDate, Date endDate) {
        try {
            //  Asegurar que startDate comienza a las 00:00:00
            // Muy importante saber que el Calendar es un ejemplo de Singleton.
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            startCalendar.set(Calendar.HOUR_OF_DAY, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);
            startCalendar.set(Calendar.MILLISECOND, 0);
            Date adjustedStartDate = startCalendar.getTime();

            //  Asegurar que endDate termina a las 23:59:59
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            endCalendar.set(Calendar.HOUR_OF_DAY, 23);
            endCalendar.set(Calendar.MINUTE, 59);
            endCalendar.set(Calendar.SECOND, 59);
            endCalendar.set(Calendar.MILLISECOND, 999);
            Date adjustedEndDate = endCalendar.getTime();

            //Consultar transacciones en MongoDB
            List<TransactionsModel> transactions = transactionRepository.findTransactionsByDateRange(adjustedStartDate, adjustedEndDate);

            // Verificar si la lista está vacía
            if (transactions.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay transacciones en el rango de fechas especificado.");
            }

            return transactions;

        } catch (ResponseStatusException e) {
            throw e; // Relanzar excepciones específicas
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener transacciones", e);
        }
    }


    // Buscar transaccion por el usuario que la realiza
    public List<TransactionsModel> getTransactionsByUserId(String userId) {
        // Convertimos el userId (String) a ObjectId
        ObjectId userObjectId = new ObjectId(userId);

        // Buscamos todas las cuentas asociadas al usuario
        List<ObjectId> accountIds = accountRepository.findByUserId(userObjectId)
                .stream()
                .map(AccountModel::getId)
                .collect(Collectors.toList());

        if (accountIds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no tiene cuentas registradas.");
        }

        // Buscamos todas las transacciones de esas cuentas
        return transactionRepository.findByAccountIds(accountIds);
    }

    // Obtener todas las transacciones del usuario autenticado
    public List<TransactionsModel> getAllTransactionsForAuthenticatedUser() {
        // Obtén el principal (usuario autenticado) desde el contexto de seguridad
        String username = getAuthenticatedUsername();
        UsersModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Usar el userId para obtener las cuentas del usuario
        List<ObjectId> accountIds = accountRepository.findByUserId(user.getId())
                .stream()
                .map(AccountModel::getId)
                .collect(Collectors.toList());

        if (accountIds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no tiene cuentas asociadas.");
        }

        // Buscar las transacciones de esas cuentas
        List<TransactionsModel> transactions = transactionRepository.findByAccountIds(accountIds);

        if (transactions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron transacciones para el usuario.");
        }

        return transactions;
    }

    // Metodo para obtener el username del usuario autenticado
    private String getAuthenticatedUsername() {
        // SecurityContextHolder se utiliza para obtener el principal autenticado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }
    }

    }

