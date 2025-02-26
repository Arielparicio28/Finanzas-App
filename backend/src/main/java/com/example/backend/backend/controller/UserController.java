package com.example.backend.backend.controller;

import com.example.backend.backend.dto.ApiResponse;
import com.example.backend.backend.dto.UpdateUserDTO;
import com.example.backend.backend.dto.UserDTO;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.services.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Obtener todos los usuarios
    @GetMapping
    public List<UsersModel> getAllUsers() {
        return userService.getAllUsers();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable String id) {

        UserDTO userPartial = userService.getUserById(id);
        ApiResponse response = new ApiResponse("Usuario obtenido correctamente", userPartial);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {

        // Validar si el ID es un ObjectId válido antes de pasar al servicio
        if (!ObjectId.isValid(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID inválido");
        }
        UsersModel updatedUser = userService.updateUser(id, updateUserDTO);
        ApiResponse response = new ApiResponse("usuario actualizado correctamente", updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String id) {
        UsersModel deletedUser = userService.deleteUser(id);
        ApiResponse response = new ApiResponse("Información y Usuario eliminado con éxito", deletedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
