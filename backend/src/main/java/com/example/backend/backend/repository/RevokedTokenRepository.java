package com.example.backend.backend.repository;

import com.example.backend.backend.model.RevokedToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RevokedTokenRepository extends MongoRepository<RevokedToken, ObjectId> {
    Optional<RevokedToken> findByToken(String token);
}
