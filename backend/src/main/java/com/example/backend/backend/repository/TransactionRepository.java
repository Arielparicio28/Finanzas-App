package com.example.backend.backend.repository;

import com.example.backend.backend.model.TransactionsModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends MongoRepository<TransactionsModel, ObjectId>
{
    //Queries de mongoDB
    //Más eficiente → MongoDB ya optimiza la búsqueda con índices en dateOfTransaction
    @Query("{ 'dateOfTransaction': { $gte: ?0, $lte: ?1 } }")
    List<TransactionsModel> findTransactionsByDateRange(Date startDate, Date endDate);

    //Obtener la transaccion por el usuario q la realiza.
    @Query("{ 'accountId': { $in: ?0 } }")
    List<TransactionsModel> findByAccountIds(List<ObjectId> accountIds);

  //Elimina las transacciones asociadas a una cuenta en particular
    void deleteByAccountId(ObjectId accountId);

    //Elimina todas las transacciones asociadas a las cuentas de un mismo usuario
    void deleteByAccountIdIn(List<ObjectId> accountIds);

}






