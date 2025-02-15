package com.example.backend.backend.services;

import com.example.backend.backend.dto.UpdateUserDTO;
import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.model.Profile;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.repository.AccountRepository;
import com.example.backend.backend.repository.UserRepository;
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
    private AccountRepository accountRepository; // Inyección de repositorio de cuentas

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
            Profile profile = new Profile(
                    updateUserDTO.getProfile().getFirstName(),
                    updateUserDTO.getProfile().getLastName(),
                    updateUserDTO.getProfile().getAge(),
                    updateUserDTO.getProfile().getPhone()
            );
            user.setProfile(profile);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());

            return userRepository.save(user);

        }catch (Exception e)
        {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el usuario", e);
        }



    }

    // Eliminar usuario
    public UsersModel deleteUser(String id) {
        UsersModel existingUser = getUserById(id); // Verifica si el usuario existe
        accountRepository.deleteByUserId(existingUser.getId());
        userRepository.delete(existingUser);
        return existingUser;
    }

}
