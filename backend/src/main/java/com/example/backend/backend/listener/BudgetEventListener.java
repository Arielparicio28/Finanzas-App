package com.example.backend.backend.listener;

import com.example.backend.backend.event.BudgetExceededEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BudgetEventListener {

    @Value("${n8n.webhook.url}")
    private String n8nWebhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @EventListener
    public void handleBudgetExceededEvent(BudgetExceededEvent event) {
        System.out.println("🚨 Evento recibido: Presupuesto Excedido");

        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", event.getUserId().toString());
        payload.put("spent", event.getSpent());
        payload.put("limit", event.getLimit());

        restTemplate.postForObject(n8nWebhookUrl, payload, String.class);
    }
}
