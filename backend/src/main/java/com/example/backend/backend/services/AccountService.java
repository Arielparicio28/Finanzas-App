package com.example.backend.backend.services;

import com.example.backend.backend.annotation.GetAuthenticatedUser;
import com.example.backend.backend.dto.AccountDTO;
import com.example.backend.backend.dto.UpdateAccountDTO;
import com.example.backend.backend.model.AccountModel;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.repository.AccountRepository;
import com.example.backend.backend.repository.TransactionRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.util.CardNumberGenerator;
import com.example.backend.backend.util.SecurityUtils;
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


    @Autowired
    private TransactionRepository transactionRepository;

    // Metodo para obtener todas las cuentas
    // List es más efeciente a la hora de buscar datos mejora el rendimiento
    public List<AccountModel> getAllAccount(){
   return accountRepository.findAll();
    }

    //Metodo para crear una cuenta
    @GetAuthenticatedUser
    public AccountModel createAccount(AccountDTO accountDTO) {
        try {
            // Asignar el ID del usuario autenticado utilizando el aspecto AOP
            String username = SecurityUtils.getAuthenticatedUsername();
            UsersModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            // Crear la cuenta y asignar los valores
            AccountModel account = new AccountModel();
            account.setUserId(user.getId()); // Asignar el ObjectId del usuario
            account.setAccountNumber(CardNumberGenerator.generateCardNumber()); // Generar número de cuenta
            account.setAccountType(accountDTO.getAccountType());
            account.setBalance(accountDTO.getBalance());
            account.setCurrency(accountDTO.getCurrency());
            account.setCreatedAt(new Date());
            account.setUpdatedAt(new Date());

            // Guardar la cuenta en la base de datos
            return accountRepository.save(account);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al crear cuenta. Verifica que los datos introducidos son correctos.",
                    e
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
        try {
            ObjectId idObj = new ObjectId(accountId);
            return accountRepository.findById(idObj)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "La cuenta con el:  " + idObj + " no fue encontrada"
                    ));
        }catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de cuenta inválido", e);
        }
    }


    //Eliminar una cuenta por su id
    public AccountModel deleteAccount(String id)
    {
        try {
            AccountModel accountExist = getAccountById(id);
            transactionRepository.deleteByAccountId(accountExist.getId());
            accountRepository.delete(accountExist);
            return accountExist;

        }   catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de cuenta inválido", e);
        }

        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la cuenta", e);
        }
    }

    // Actualizar una cuenta por su id
    public AccountModel updateAccount(String id, UpdateAccountDTO updateAccountDTO)
    {
        try {
            AccountModel existingAccount = getAccountById(id);
            if( existingAccount == null)
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El Id no existe");
            }

            existingAccount.setAccountType(updateAccountDTO.getAccountType());
            existingAccount.setBalance(updateAccountDTO.getBalance());

            return accountRepository.save(existingAccount);
        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la cuenta", e);
        }
    }


























}
