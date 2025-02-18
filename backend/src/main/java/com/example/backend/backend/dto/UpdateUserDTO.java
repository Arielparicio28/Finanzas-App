package com.example.backend.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateUserDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotNull(message = "El perfil es obligatorio")
    private UpdateProfileDTO profile;

    // Getters y setters

    public @NotBlank(message = "El nombre de usuario es obligatorio") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "El nombre de usuario es obligatorio") String username) {
        this.username = username;
    }

    public @NotNull(message = "El perfil es obligatorio") UpdateProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(@NotNull(message = "El perfil es obligatorio") UpdateProfileDTO profile) {
        this.profile = profile;
    }
}
