package mx.example.proyecto_hexagonal.application.exceptions;

import mx.example.proyecto_hexagonal.application.exceptions.custom.DatabaseConnectionException;
import mx.example.proyecto_hexagonal.application.exceptions.custom.EmployeeAlreadyExistsException;
import mx.example.proyecto_hexagonal.application.exceptions.custom.EmployeeNotFoundException;
import mx.example.proyecto_hexagonal.application.exceptions.custom.HttpRequestFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para GlobalExceptionHandler.
 *
 * Se validan las respuestas HTTP generadas para cada excepción personalizada,
 * asegurando que el código de estado y el mensaje sean los correctos.
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    /**
     * Inicialización del handler antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    /**
     * Prueba unitaria para manejar la excepción EmployeeNotFoundException.
     *
     * Valida que el estatus HTTP sea 404 (Not Found) y el mensaje se construya correctamente.
     */
    @Test
    void testHandleNotFound() {
        EmployeeNotFoundException exception = new EmployeeNotFoundException(1L);
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Employee with ID 1 not found.", response.getBody().get("message"));
        assertEquals(404, response.getBody().get("status"));
    }

    /**
     * Prueba unitaria para manejar la excepción EmployeeAlreadyExistsException.
     *
     * Valida que el estatus HTTP sea 409 (Conflict) y el mensaje se construya correctamente.
     */
    @Test
    void testHandleAlreadyExists() {
        EmployeeAlreadyExistsException exception = new EmployeeAlreadyExistsException("john.doe@example.com");
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleAlreadyExists(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("john.doe@example.com", response.getBody().get("message"));
        assertEquals(409, response.getBody().get("status"));
    }

    /**
     * Prueba unitaria para manejar la excepción DatabaseConnectionException.
     *
     * Valida que el estatus HTTP sea 503 (Service Unavailable) y el mensaje se construya correctamente.
     */
    @Test
    void testHandleDatabaseConnection() {
        DatabaseConnectionException exception = new DatabaseConnectionException("Timeout connecting to DB");
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleDatabaseConnection(exception);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Database connection failed: Timeout connecting to DB", response.getBody().get("message"));
        assertEquals(503, response.getBody().get("status"));
    }

    /**
     * Prueba unitaria para manejar la excepción HttpRequestFailedException.
     *
     * Valida que el estatus HTTP sea 502 (Bad Gateway) y el mensaje se construya correctamente.
     */
    @Test
    void testHandleHttpRequestFailed() {
        HttpRequestFailedException exception = new HttpRequestFailedException("External API error");
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleHttpRequestFailed(exception);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("HTTP request failed: External API error", response.getBody().get("message"));
        assertEquals(502, response.getBody().get("status"));
    }

    /**
     * Prueba unitaria para manejar excepciones genéricas (Exception).
     *
     * Valida que el estatus HTTP sea 500 (Internal Server Error) y el mensaje se construya correctamente.
     */
    @Test
    void testHandleGeneralException() {
        Exception exception = new Exception("Unexpected error occurred");
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().get("message"));
        assertEquals(500, response.getBody().get("status"));
    }
}
