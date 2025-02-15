package com.example.backend.backend.dto;

import com.example.backend.backend.enums.AccountTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountDTO {

    @NotBlank(message = "El ID del usuario es obligatorio")
    private String userId;  // Recibido como String, luego se convertirá a ObjectId

    @NotNull(message = "El accountType es de tipo CORRIENTE || AHORRO")
    private AccountTypes accountType;

    @NotNull(message = "El balance es obligatorio ")
    private Double balance;

    @NotBlank(message = "La moneda es de tipo EUR || USD, etc.")
    private String moneda;

    public @NotBlank(message = "El ID del usuario es obligatorio") String getUserId() {
        return userId;
    }

    public void setUserId(@NotBlank(message = "El ID del usuario es obligatorio") String userId) {
        this.userId = userId;
    }

    public @NotNull(message = "El accountType es de tipo CORRIENTE || AHORRO") AccountTypes getAccountType() {
        return accountType;
    }

    public void setAccountType(@NotNull(message = "El accountType es de tipo CORRIENTE || AHORRO") AccountTypes accountType) {
        this.accountType = accountType;
    }

    public @NotNull(message = "El balance es obligatorio ") Double getBalance() {
        return balance;
    }

    public void setBalance(@NotNull(message = "El balance es obligatorio ") Double balance) {
        this.balance = balance;
    }

    public @NotBlank(message = "La moneda es de tipo EUR || USD, etc.") String getMoneda() {
        return moneda;
    }

    public void setMoneda(@NotBlank(message = "La moneda es de tipo EUR || USD, etc.") String moneda) {
        this.moneda = moneda;
    }
}
