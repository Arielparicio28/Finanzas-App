package com.example.backend.backend.repository;

import com.example.backend.backend.enums.Category;
import com.example.backend.backend.model.BudgetModel;
import com.example.backend.backend.model.Period;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends MongoRepository<BudgetModel, ObjectId> {

    List<BudgetModel> findByUserId(ObjectId userId);
    Optional<BudgetModel> findByUserIdAndCategoryAndPeriod(ObjectId userId, Category category, Period period);

}
