package com.example.backend.backend.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Cuando es null no lo incluye en la respuesta, me sirve aqui porq no devuelvo la password.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "El perfil es obligatorio")
    private ProfileDTO profile;

    // Getters y setters

    public @NotBlank(message = "El nombre de usuario es obligatorio") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "El nombre de usuario es obligatorio") String username) {
        this.username = username;
    }

    public @NotBlank(message = "El correo electrónico es obligatorio") @Email(message = "El correo electrónico debe tener un formato válido") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "El correo electrónico es obligatorio") @Email(message = "El correo electrónico debe tener un formato válido") String email) {
        this.email = email;
    }

    public @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password) {
        this.password = password;
    }

    public @NotNull(message = "El perfil es obligatorio") ProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(@NotNull(message = "El perfil es obligatorio") ProfileDTO profile) {
        this.profile = profile;
    }
}
