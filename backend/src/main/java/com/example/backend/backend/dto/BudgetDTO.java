package com.example.backend.backend.dto;

import com.example.backend.backend.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class BudgetDTO {

    @NotBlank(message = "El ID del usuario es obligatorio")
    private String userId;  // Recibido como String, luego se convertirá a ObjectId

    @NotNull(message = "Categorias es obligatoria")
    private Category category;

    @NotNull(message = "El limite de gasto por mes es obligatorio ")
    @PositiveOrZero(message = "El límite debe ser positivo o cero")
    private Double limit;

    @NotNull(message = "El período es obligatorio")
    private PeriodDTO period;

    private Double spent;
    public Double getSpent() {
        return spent;
    }

    public void setSpent(Double spent) {
        this.spent = spent;
    }



    public @NotBlank(message = "El ID del usuario es obligatorio") String getUserId() {
        return userId;
    }

    public void setUserId(@NotBlank(message = "El ID del usuario es obligatorio") String userId) {
        this.userId = userId;
    }

    public @NotNull(message = "El limite de gasto por mes es obligatorio ") @PositiveOrZero(message = "El límite debe ser positivo o cero") Double getLimit() {
        return limit;
    }

    public void setLimit(@NotNull(message = "El limite de gasto por mes es obligatorio ") @PositiveOrZero(message = "El límite debe ser positivo o cero") Double limit) {
        this.limit = limit;
    }

    public @NotNull(message = "Categorias es obligatoria") Category getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Categorias es obligatoria") Category category) {
        this.category = category;
    }

    public @NotNull(message = "El período es obligatorio") PeriodDTO getPeriod() {
        return period;
    }

    public void setPeriod(@NotNull(message = "El período es obligatorio") PeriodDTO period) {
        this.period = period;
    }


}
