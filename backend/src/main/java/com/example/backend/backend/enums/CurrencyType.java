package com.example.backend.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CurrencyType {

    EUR("Eur"),
    USD("Usd");

    private final String value;

    CurrencyType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CurrencyType fromString(String value) {
        for (CurrencyType type : CurrencyType.values()) {
            if (type.value.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

}
