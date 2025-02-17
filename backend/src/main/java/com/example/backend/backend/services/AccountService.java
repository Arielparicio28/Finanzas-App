package com.example.backend.backend.services;

import com.example.backend.backend.dto.AccountDTO;
import com.example.backend.backend.dto.UpdateAccountDTO;
import com.example.backend.backend.model.AccountModel;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.repository.AccountRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.util.CardNumberGenerator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    // Metodo para obtener todas las cuentas
    // List es más efeciente a la hora de buscar datos mejora el rendimiento
    public List<AccountModel> getAllAccount(){
   return accountRepository.findAll();
    }

    //Metodo para crear una cuenta
    public AccountModel createAccount(AccountDTO accountDTO) {
        // Validar que el usuario exista (se asume que el ID se pasa como String)
        UsersModel user = userRepository.findById(accountDTO.getUserId())
                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        try {
          // Convertir el userId a ObjectId
          ObjectId userIdObj = new ObjectId(accountDTO.getUserId());
          // Crear la cuenta y asignar las fechas
          AccountModel account = new AccountModel();
          account.setUserId(userIdObj);
          // Generar el número de cuenta automáticamente
          String generatedNumber = CardNumberGenerator.generateCardNumber();
          account.setAccountNumber(generatedNumber);
          account.setAccountType(accountDTO.getAccountType());
          account.setBalance(accountDTO.getBalance());
          account.setCurrency(accountDTO.getCurrency());
          account.setCreatedAt(new Date());
          account.setUpdatedAt(new Date());

          return accountRepository.save(account);
      } catch (Exception e) {
          throw new ResponseStatusException(
                  HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear cuenta verifica que los datos introducidos son correctos.", e
          );
      }
    }

    // Metodo para obtener la informacion de un usuario por el id de su cuenta
    public AccountModel getUserByAccountId(String accountId) {
        try {
            ObjectId idObj = new ObjectId(accountId);
            return accountRepository.findById(idObj)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de cuenta inválido", e);
        }
    }

    //Obtener la información de una cuenta por su Id
    public AccountModel getAccountById(String accountId){
        ObjectId idObj = new ObjectId(accountId);
        return accountRepository.findById(idObj)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La cuenta con el:  " + idObj + " no fue encontrada"
                ));
    }


    //Eliminar una cuenta por su id
    public AccountModel deleteAccount(String id)
    {
        try {
            AccountModel accountExist = getAccountById(id);
            accountRepository.delete(accountExist);
            return accountExist;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Actualizar una cuenta por su id
    public AccountModel updateAccount(String id, UpdateAccountDTO updateAccountDTO)
    {
        try {
            AccountModel existingAccount = getAccountById(id);
            existingAccount.setAccountType(updateAccountDTO.getAccountType());
            existingAccount.setBalance(updateAccountDTO.getBalance());

            return accountRepository.save(existingAccount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


























}
