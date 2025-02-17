package com.example.backend.backend.repository;

import com.example.backend.backend.model.TransactionsModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<TransactionsModel, ObjectId> {
}
