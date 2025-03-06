package com.example.backend.backend.aspect;

import com.example.backend.backend.event.BudgetExceededEvent;
import com.example.backend.backend.model.BudgetModel;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.publisher.BudgetEventPublisher;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAspect {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BudgetEventPublisher budgetEventPublisher;

    @AfterReturning("@annotation(com.example.backend.backend.annotation.GetAuthenticatedUser)")
    public void injectUser(JoinPoint joinPoint) {
        String username = SecurityUtils.getAuthenticatedUsername();
        UsersModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        System.out.println("🔥 Usuario autenticado: " + user.getUsername());
    }

    // Si el usuario gasta más del límite se lance este evento.
    @AfterReturning(value = "@annotation(com.example.backend.backend.annotation.GetAuthenticatedUser)", returning = "result")
    public void setUserId(JoinPoint joinPoint, Object result) {
        if (result instanceof BudgetModel budget) {
            String username = SecurityUtils.getAuthenticatedUsername();
            UsersModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            budget.setUserId(user.getId());

            if (budget.getSpent() > budget.getLimit()) {
                System.out.println("🔥 El usuario se pasó del presupuesto!");

                BudgetExceededEvent event = new BudgetExceededEvent(this, user.getId(), budget.getSpent(), budget.getLimit());
                budgetEventPublisher.publishBudgetExceededEvent(event);
            }
        }
    }


}
