package com.example.backend.backend.enums;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountTypes {
    CORRIENTE("Corriente"),
    AHORRO("Ahorro");

    private final String value;

    AccountTypes(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AccountTypes fromString(String value) {
        for (AccountTypes type : AccountTypes.values()) {
            if (type.value.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
