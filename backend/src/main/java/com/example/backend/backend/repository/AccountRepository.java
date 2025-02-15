package com.example.backend.backend.repository;

import com.example.backend.backend.model.AccountModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<AccountModel, ObjectId> {
    void deleteByUserId(ObjectId userId);
}
