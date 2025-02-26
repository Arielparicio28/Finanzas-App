package com.example.backend.backend.repository;

import com.example.backend.backend.model.UsersModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UsersModel, ObjectId> {
    Optional<UsersModel> findByUsername(String username);
}
