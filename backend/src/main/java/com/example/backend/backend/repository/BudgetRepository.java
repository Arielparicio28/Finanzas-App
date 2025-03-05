package com.example.backend.backend.repository;

import com.example.backend.backend.model.BudgetModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BudgetRepository extends MongoRepository<BudgetModel, ObjectId> {

    List<BudgetModel> findByUserId(ObjectId userId);
}
