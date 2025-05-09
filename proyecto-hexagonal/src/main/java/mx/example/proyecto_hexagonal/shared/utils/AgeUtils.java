package mx.example.proyecto_hexagonal.shared.utils;

import lombok.extern.slf4j.Slf4j;
import mx.example.proyecto_hexagonal.application.exceptions.custom.AgeException;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
public class AgeUtils {

    /**
     * Calculate age in years
     * @param birthDate the date of birth (must not be null)
     * @return age
     * @throws AgeException
     */
    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            log.error("Invalid birth date");
            throw new AgeException();
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
