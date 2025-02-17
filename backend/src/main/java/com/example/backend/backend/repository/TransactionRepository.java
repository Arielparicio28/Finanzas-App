package com.example.backend.backend.repository;

import com.example.backend.backend.model.TransactionsModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends MongoRepository<TransactionsModel, ObjectId>
{
    //Query de mongoDB
    //Más eficiente → MongoDB ya optimiza la búsqueda con índices en dateOfTransaction
    @Query("{ 'dateOfTransaction': { $gte: ?0, $lte: ?1 } }")
    List<TransactionsModel> findTransactionsByDateRange(Date startDate, Date endDate);
}






