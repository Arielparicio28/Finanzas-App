package com.example.backend.backend.services;

import com.example.backend.backend.dto.BudgetDTO;
import com.example.backend.backend.dto.PeriodDTO;
import com.example.backend.backend.model.BudgetModel;
import com.example.backend.backend.model.Period;
import com.example.backend.backend.model.UsersModel;
import com.example.backend.backend.repository.BudgetRepository;
import com.example.backend.backend.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    // Metodo para crear un presupuesto
    public BudgetModel createBudget(BudgetDTO budgetDTO) {
        // Convertir el userId a ObjectId y validar que el usuario exista
        if (!ObjectId.isValid(budgetDTO.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de usuario inválido");
        }
        ObjectId userIdObj = new ObjectId(budgetDTO.getUserId());
        UsersModel user = userRepository.findById(userIdObj)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Mapear los campos del DTO al modelo
        BudgetModel budget = new BudgetModel();
        budget.setUserId(userIdObj);
        budget.setCategory(budgetDTO.getCategory());
        budget.setLimit(budgetDTO.getLimit());
        // Si no se envía spent, se inicializa en 0
        budget.setSpent(budgetDTO.getSpent() != null ? budgetDTO.getSpent() : 0.0);
        // Convertir PeriodDTO a Period
        budget.setPeriod(convertPeriodDTOToPeriod(budgetDTO.getPeriod()));
        return budgetRepository.save(budget);
    }

    private Period convertPeriodDTOToPeriod(PeriodDTO periodDTO) {
        if (periodDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El período es obligatorio");
        }
        // Suponiendo que Period tiene un constructor que recibe LocalDate para start y end
        return new Period(periodDTO.getStart(), periodDTO.getEnd());
    }
}
