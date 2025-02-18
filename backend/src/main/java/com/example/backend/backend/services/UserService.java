package com.example.backend.backend.services;

import com.example.backend.backend.dto.UpdateUserDTO;
import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.model.AccountModel;
import com.example.backend.backend.model.Profile;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.repository.AccountRepository;
import com.example.backend.backend.repository.TransactionRepository;
import com.example.backend.backend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;// Inyección de repositorio de cuentas

    @Autowired
    private TransactionRepository transactionRepository;

    // Crear usuario
    public UsersModel addUser(UserDTO userDTO) {
        try {
            UsersModel user = new UsersModel();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            // Aquí puedes encriptar la contraseña y asignarla a passwordHash
            user.setPasswordHash(userDTO.getPassword());
            // Convertir el ProfileDTO a Profile
            if (userDTO.getProfile() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El perfil no puede ser nulo");
            }

            Profile profile = new Profile(
                    userDTO.getProfile().getFirstName(),
                    userDTO.getProfile().getLastName(),
                    userDTO.getProfile().getAge(),
                    userDTO.getProfile().getPhone()
            );
            user.setProfile(profile);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());

            return userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el usuario", e);
        }
    }

    // Obtener todos los usuarios
    public List<UsersModel> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener usuario por ID
    public UsersModel getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "El usuario con el id:  " + id + " no fue encontrada"
                ));
    }

    // Actualizar usuario
    public UsersModel updateUser(String id, UpdateUserDTO updateUserDTO)
    {
        try{
            // Verifica si el usuario existe
            UsersModel user = getUserById(id);
            user.setUsername(updateUserDTO.getUsername());

            // Convertir el ProfileDTO a Profile
            if (updateUserDTO.getProfile() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El perfil no puede ser nulo");
            }

            if(updateUserDTO.getProfile().getAge() == null)
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La edad no puede ser nula.");
            }

            if(updateUserDTO.getProfile().getPhone() == null)
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El telefono no puede ser nulo.");
            }
            // Obtener el perfil actual del usuario
            Profile profile = user.getProfile();
            if (profile == null) {
                // Si el usuario no tiene un perfil, lanzamos error (ya que no queremos crear uno nuevo)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no tiene un perfil asignado");
            }
            // Actualizar solo los campos deseados: age y phone
            profile.setAge(updateUserDTO.getProfile().getAge());
            profile.setPhone(updateUserDTO.getProfile().getPhone());

            // Actualizar la fecha de modificación
            user.setUpdatedAt(new Date());
            return userRepository.save(user);

        }catch(ResponseStatusException e)
        {
            throw e;
        }

        catch (Exception e)
        {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el usuario", e);
        }



    }

    // Eliminar usuario
    public UsersModel deleteUser(String id) {
        UsersModel existingUser = getUserById(id);// Verifica si el usuario existe
        // Obtiene todas las cuentas asociadas al usuario
        List<AccountModel> userAccounts = accountRepository.findByUserId(existingUser.getId());

        // Extrae los IDs de las cuentas del usuario
        List<ObjectId> accountIds = userAccounts.stream()
                .map(AccountModel::getId)
                .toList();
        // Elimina todas las transacciones asociadas a esas cuentas
        transactionRepository.deleteByAccountIdIn(accountIds);
        // Elimina todas las cuentas asociadas al usuario
        accountRepository.deleteByUserId(existingUser.getId());
        // Finalmente, elimina el usuario
        userRepository.delete(existingUser);
        return existingUser;
    }
}
