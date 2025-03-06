package com.example.backend.backend.services;

import com.example.backend.backend.annotation.GetAuthenticatedUser;
import com.example.backend.backend.dto.BudgetDTO;
import com.example.backend.backend.dto.PeriodDTO;
import com.example.backend.backend.model.*;
import com.example.backend.backend.repository.BudgetRepository;
import com.example.backend.backend.repository.UserRepository;
import com.example.backend.backend.util.SecurityUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    // Metodo para crear un presupuesto
    @GetAuthenticatedUser
    @Transactional
    public BudgetModel createBudget(BudgetDTO budgetDTO) {
        try {
            // Mapear los campos del DTO al modelo
            BudgetModel budget = new BudgetModel();
            budget.setCategory(budgetDTO.getCategory());
            budget.setLimit(budgetDTO.getLimit());
            // Si no se envía spent, se inicializa en 0
            budget.setSpent(budgetDTO.getSpent() != null ? budgetDTO.getSpent() : 0.0);
            // Convertir PeriodDTO a Period
            budget.setPeriod(convertPeriodDTOToPeriod(budgetDTO.getPeriod()));
            // Asignar el ID del usuario autenticado utilizando el aspecto AOP
            String username = SecurityUtils.getAuthenticatedUsername();
            UsersModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            budget.setUserId(user.getId());
            return budgetRepository.save(budget);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Period convertPeriodDTOToPeriod(PeriodDTO periodDTO) {
        if (periodDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El período es obligatorio");
        }
        // Suponiendo que Period tiene un constructor que recibe LocalDate para start y end
        return new Period(periodDTO.getStart(), periodDTO.getEnd());
    }

    @GetAuthenticatedUser
    public List<BudgetModel> getAllBudgetForAuthUser()
    {
        String username = SecurityUtils.getAuthenticatedUsername();
        UsersModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Usar el userId para obtener las cuentas del usuario
        List<ObjectId> userId = budgetRepository.findByUserId(user.getId())
                .stream()
                .map(BudgetModel::getId)
                .toList();

        if (userId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no tiene presupuestos asociadas.");
        }

        // Buscar los presupuestos del usuario.
        List<BudgetModel> budget = budgetRepository.findByUserId(user.getId());

        if (budget.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron transacciones para el usuario.");
        }

        return budget;
    }
}
