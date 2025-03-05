package com.example.backend.backend.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class PeriodDTO {

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate start;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate end;

    public PeriodDTO() {}

    public PeriodDTO(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio");
        }
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
