package com.example.backend.backend.model;

import java.time.LocalDate;

public class Period {
    private LocalDate start;
    private LocalDate end;

    public Period() {
    }

    public Period(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la fecha de inicio");
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

    @Override
    public String toString() {
        return "Period{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}

