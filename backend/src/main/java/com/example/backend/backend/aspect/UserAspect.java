package com.example.backend.backend.aspect;

import com.example.backend.backend.model.BudgetModel;
import com.example.backend.backend.model.UsersModel;
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

    @AfterReturning("@annotation(com.example.backend.backend.annotation.GetAuthenticatedUser)")
    public void injectUser(JoinPoint joinPoint) {
        String username = SecurityUtils.getAuthenticatedUsername();
        UsersModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        System.out.println("🔥 Usuario autenticado: " + user.getUsername());
    }

    @AfterReturning(value = "@annotation(com.example.backend.backend.annotation.GetAuthenticatedUser)", returning = "result")
    public void setUserId(JoinPoint joinPoint, Object result) {
        if (result instanceof BudgetModel) {
            String username = SecurityUtils.getAuthenticatedUsername();
            UsersModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

            ((BudgetModel) result).setUserId(user.getId());
        }

    }

}
