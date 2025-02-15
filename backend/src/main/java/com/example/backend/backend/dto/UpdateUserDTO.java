package com.example.backend.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateUserDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotNull(message = "El perfil es obligatorio")
    private ProfileDTO profile;

    // Getters y setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public ProfileDTO getProfile() {
        return profile;
    }
    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }
}
