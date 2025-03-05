package com.example.backend.backend.dto;

import com.example.backend.backend.enums.AccountTypes;
import com.example.backend.backend.enums.CurrencyType;
import jakarta.validation.constraints.NotNull;


public class AccountDTO {

    @NotNull(message = "El accountType es de tipo CORRIENTE || AHORRO")
    private AccountTypes accountType;

    @NotNull(message = "El balance es obligatorio ")
    private Double balance;

    @NotNull(message = "La moneda es de tipo EUR || USD, etc.")
    private CurrencyType currency;


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

    public @NotNull(message = "La moneda es de tipo EUR || USD, etc.") CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(@NotNull(message = "La moneda es de tipo EUR || USD, etc.") CurrencyType currency) {
        this.currency = currency;
    }
}
