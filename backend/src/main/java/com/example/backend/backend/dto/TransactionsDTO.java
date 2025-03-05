package com.example.backend.backend.dto;


import com.example.backend.backend.enums.Category;
import com.example.backend.backend.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class TransactionsDTO
{

    @NotBlank(message = "El ID de la cuenta es obligatorio")
    private String accountId;  // Recibido como String, luego se convertirá a ObjectId

    @NotNull(message = "El amount no puede ser nulo")
    private Double amount;

    @NotNull(message = "Transaction type es de tipo Credito | Debito ")
    private TransactionType type;

    public @NotNull(message = "Categoria del gasto alimentacion,ocio,salario, factura,etc.") Category getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Categoria del gasto alimentacion,ocio,salario, factura,etc.") Category category) {
        this.category = category;
    }

    @NotNull(message = "Categoria del gasto alimentacion,ocio,salario, factura,etc.")
    private Category category;


    private String description;

    public @NotBlank(message = "El ID de la cuenta es obligatorio") String getAccountId() {
        return accountId;
    }

    public void setAccountId(@NotBlank(message = "El ID de la cuenta es obligatorio") String accountId) {
        this.accountId = accountId;
    }

    public @NotNull(message = "El amount puede ser + o -") Double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "El amount puede ser + o -") Double amount) {
        this.amount = amount;
    }

    public @NotNull(message = "Transaction type es de tipo Credito | Debito ") TransactionType getType() {
        return type;
    }

    public void setType(@NotNull(message = "Transaction type es de tipo Credito | Debito ") TransactionType type) {
        this.type = type;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
