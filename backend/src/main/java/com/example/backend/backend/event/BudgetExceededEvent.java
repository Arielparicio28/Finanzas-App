package com.example.backend.backend.event;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEvent;

@Getter
public class BudgetExceededEvent extends ApplicationEvent {

    private final ObjectId userId;
    private final Double spent;
    private final Double limit;

    public Double getSpent() {
        return spent;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public Double getLimit() {
        return limit;
    }

    public BudgetExceededEvent(Object source, ObjectId userId, Double spent, Double limit) {
        super(source);
        this.userId = userId;
        this.spent = spent;
        this.limit = limit;
    }

}
