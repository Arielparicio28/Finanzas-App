package com.example.backend.backend.model;

import com.example.backend.backend.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "transaction")
@Data
public class TransactionsModel
{

    @Id
    private ObjectId id;
    private ObjectId accountId;// Id de la cuenta asociada.

  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
    // Para hacer la peticion esta en el formato YYYY MM dd
    private Date dateOfTransaction;//Fecha y hora de la transaccion.

    private Double amount; // positivo para ingreso y negativo para gastos
    private TransactionType type; // Credito o debito
    private String category; //categoria del gasto alimentacion,ocio,salario, factura,etc.
    private String description;

    //Getters and Setter


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getAccountId() {
        return accountId;
    }

    public void setAccountId(ObjectId accountId) {
        this.accountId = accountId;
    }

    public Date getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(Date dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
