package com.example.backend.backend.model;

import com.example.backend.backend.enums.Category;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "budget")
@Data
public class BudgetModel {

    @Id
    private ObjectId id;
    private ObjectId userId;
    private Category category; // Categoría del gasto.
    private Double limit; //Limite de gasto por mes.
    private Period period; //Intervalo del presupuesto
    private Double spent; // Monto ya gastado
    // Luego crear alertas usando n8n.

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Double getSpent() {
        return spent;
    }

    public void setSpent(Double spent) {
        this.spent = spent;
    }

}
