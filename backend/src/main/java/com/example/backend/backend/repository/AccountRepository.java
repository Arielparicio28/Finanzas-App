package com.example.backend.backend.repository;

import com.example.backend.backend.model.AccountModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<AccountModel, ObjectId> {

    void deleteByUserId(ObjectId userId);
    List<AccountModel> findByUserId(ObjectId userId);

}
