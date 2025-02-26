package com.example.backend.backend.model;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "revoked_tokens")
@Data
public class RevokedToken {

        @Id
        private ObjectId id;

        @Field("token")
        @Indexed(unique = true)
        private String token;

        @Field("username")
        private String username;

        @Field("expirationDate")
        private Date expirationDate;

        public RevokedToken() {}

        public RevokedToken(String token, String username, Date expirationDate) {
            this.token = token;
            this.username = username;
            this.expirationDate = expirationDate;
        }
    }
