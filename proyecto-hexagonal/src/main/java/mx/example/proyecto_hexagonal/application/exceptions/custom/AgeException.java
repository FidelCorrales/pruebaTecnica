package mx.example.proyecto_hexagonal.application.exceptions.custom;

public class AgeException extends RuntimeException {
    public AgeException() {
        super("Birth date must not be null or greater than current date");
    }
}
