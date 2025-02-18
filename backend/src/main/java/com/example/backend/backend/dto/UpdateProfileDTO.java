package com.example.backend.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateProfileDTO {

    @NotNull(message = "La edad es obligatoria")
    private Integer age;

    @NotBlank(message = "El teléfono es obligatorio")
    private String phone;

    public @NotNull(message = "La edad es obligatoria") Integer getAge() {
        return age;
    }

    public void setAge(@NotNull(message = "La edad es obligatoria") Integer age) {
        this.age = age;
    }

    public @NotBlank(message = "El teléfono es obligatorio") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "El teléfono es obligatorio") String phone) {
        this.phone = phone;
    }
}
