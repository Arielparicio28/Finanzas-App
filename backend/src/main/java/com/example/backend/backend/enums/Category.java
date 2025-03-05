package com.example.backend.backend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {

    ALIMENTACION("Alimentación"),
    SHOPPING("Shopping"),
    SUPERMERCADOS("Supermercados"),
    CASA("Casa"),
    TRANSPORTE("Transporte"),
    OCIO("Ocio"),
    EDUCACION("Educación"),
    SALUD("Salud"),
    SERVICIOS("Servicios"),
    SALARIO("Salario"),
    OTROS("Otros");

    private final String value;
    //Constructor
    Category(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue()
    {
        return value;
    }

    @JsonCreator
    public static Category fromString(String value)
    {
        for(Category category: Category.values())
        {
            if(category.value.equalsIgnoreCase(value) || category.name().equalsIgnoreCase(value))
            {
                return category;
            }
        }
        throw  new IllegalArgumentException("No enum constant for value: " + value );
    }

}
