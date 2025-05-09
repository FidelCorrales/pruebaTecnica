package mx.example.proyecto_hexagonal.application.exceptions.custom;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
