package com.example.backend.backend.repository;

import com.example.backend.backend.model.UsersModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UsersModel,String> {

}
