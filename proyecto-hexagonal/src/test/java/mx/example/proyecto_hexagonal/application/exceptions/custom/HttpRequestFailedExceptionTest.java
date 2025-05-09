package mx.example.proyecto_hexagonal.application.exceptions.custom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para la excepción personalizada HttpRequestFailedException.
 *
 * Se validan los constructores de la excepción y la correcta propagación
 * de los mensajes y causas.
 */
class HttpRequestFailedExceptionTest {

    private static final String ERROR_MESSAGE = "HTTP request failed";
    private static final Throwable CAUSE = new RuntimeException("Root cause exception");

    /**
     * Prueba unitaria para el constructor que recibe solo un mensaje.
     *
     * Valida que el mensaje se almacene correctamente en la excepción.
     */
    @Test
    void testConstructorWithMessage() {
        // Act
        HttpRequestFailedException exception = new HttpRequestFailedException(ERROR_MESSAGE);

        // Assert
        assertNotNull(exception, "La excepción no debería ser nula");
        assertEquals(ERROR_MESSAGE, exception.getMessage(), "El mensaje de error no coincide");
    }

    /**
     * Prueba unitaria para el constructor que recibe un mensaje y una causa.
     *
     * Valida que tanto el mensaje como la causa se almacenen correctamente en la excepción.
     */
    @Test
    void testConstructorWithMessageAndCause() {
        // Act
        HttpRequestFailedException exception = new HttpRequestFailedException(ERROR_MESSAGE, CAUSE);

        // Assert
        assertNotNull(exception, "La excepción no debería ser nula");
        assertEquals(ERROR_MESSAGE, exception.getMessage(), "El mensaje de error no coincide");
        assertEquals(CAUSE, exception.getCause(), "La causa no se asignó correctamente");
    }
}
