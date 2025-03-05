package com.example.backend.backend.publisher;

import com.example.backend.backend.event.BudgetExceededEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BudgetEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publishBudgetExceededEvent(BudgetExceededEvent event) {
        System.out.println("🚨 Evento lanzado: Presupuesto Excedido para el usuario: " + event.getUserId());
        publisher.publishEvent(event);
    }
}
