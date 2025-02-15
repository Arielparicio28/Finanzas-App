package com.example.backend.backend.controller;

import com.example.backend.backend.dto.ApiResponse;
import com.example.backend.backend.dto.UpdateUserDTO;
import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Crear usuario
    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody UserDTO userDTO) {
        UsersModel savedUser = userService.addUser(userDTO);
        ApiResponse response = new ApiResponse("Usuario creado con éxito", savedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener todos los usuarios
    @GetMapping
    public List<UsersModel> getAllUsers() {
        return userService.getAllUsers();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public UsersModel getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        UsersModel updatedUser = userService.updateUser(id, updateUserDTO);
        ApiResponse response = new ApiResponse("usuario actualizado correctamente",updatedUser);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String id) {
        UsersModel deletedUser = userService.deleteUser(id);
        ApiResponse response = new ApiResponse("Información y Usuario eliminado con éxito", deletedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
