package mx.example.proyecto_hexagonal.application.exceptions.custom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para la excepción personalizada EmployeeAlreadyExistsException.
 *
 * Se valida que el mensaje generado sea correcto al construir la excepción.
 */
class EmployeeAlreadyExistsExceptionTest {

    private static final String ERROR_MESSAGE = "Employee with email john.doe@example.com already exists.";

    /**
     * Prueba unitaria para el constructor que recibe un mensaje.
     *
     * Valida que el mensaje se almacene correctamente en la excepción.
     */
    @Test
    void testConstructorWithMessage() {
        // Act
        EmployeeAlreadyExistsException exception = new EmployeeAlreadyExistsException(ERROR_MESSAGE);

        // Assert
        assertNotNull(exception, "La excepción no debería ser nula");
        assertEquals(ERROR_MESSAGE, exception.getMessage(), "El mensaje de error no coincide con el esperado");
    }
}
