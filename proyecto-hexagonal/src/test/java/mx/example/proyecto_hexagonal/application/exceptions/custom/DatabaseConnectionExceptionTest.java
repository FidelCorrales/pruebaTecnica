package mx.example.proyecto_hexagonal.application.exceptions.custom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para la excepción personalizada DatabaseConnectionException.
 *
 * Se valida que el mensaje generado y la causa se almacenen correctamente.
 */
class DatabaseConnectionExceptionTest {

    private static final String ERROR_MESSAGE = "Failed to connect to the database";
    private static final Throwable CAUSE = new RuntimeException("Root cause exception");

    /**
     * Prueba unitaria para el constructor que recibe solo un mensaje.
     *
     * Valida que el mensaje se almacene correctamente en la excepción.
     */
    @Test
    void testConstructorWithMessage() {
        // Act
        DatabaseConnectionException exception = new DatabaseConnectionException(ERROR_MESSAGE);

        // Assert
        assertNotNull(exception, "La excepción no debería ser nula");
        assertEquals(ERROR_MESSAGE, exception.getMessage(), "El mensaje de error no coincide con el esperado");
    }

    /**
     * Prueba unitaria para el constructor que recibe un mensaje y una causa.
     *
     * Valida que tanto el mensaje como la causa se almacenen correctamente en la excepción.
     */
    @Test
    void testConstructorWithMessageAndCause() {
        // Act
        DatabaseConnectionException exception = new DatabaseConnectionException(ERROR_MESSAGE, CAUSE);

        // Assert
        assertNotNull(exception, "La excepción no debería ser nula");
        assertEquals(ERROR_MESSAGE, exception.getMessage(), "El mensaje de error no coincide con el esperado");
        assertEquals(CAUSE, exception.getCause(), "La causa de la excepción no se asignó correctamente");
    }
}
