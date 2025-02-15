package com.example.backend.backend.dto;

import com.example.backend.backend.enums.AccountTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateAccountDTO {

    @NotNull(message = "El accountType es de tipo CORRIENTE || AHORRO")
    private AccountTypes accountType;

    @NotNull(message = "El balance es obligatorio ")
    private Double balance;

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
}
