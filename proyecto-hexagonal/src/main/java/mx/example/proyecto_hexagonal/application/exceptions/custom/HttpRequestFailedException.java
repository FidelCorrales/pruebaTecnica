package mx.example.proyecto_hexagonal.application.exceptions.custom;

public class HttpRequestFailedException extends RuntimeException {
    public HttpRequestFailedException(String message) {
        super(message);
    }

    public HttpRequestFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}