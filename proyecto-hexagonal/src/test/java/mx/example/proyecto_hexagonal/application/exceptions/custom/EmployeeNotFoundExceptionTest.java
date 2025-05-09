package mx.example.proyecto_hexagonal.application.exceptions.custom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para la excepción personalizada EmployeeNotFoundException.
 *
 * Se valida que el mensaje generado sea correcto al construir la excepción.
 */
class EmployeeNotFoundExceptionTest {

    private static final Long EMPLOYEE_ID = 1L;
    private static final String EXPECTED_MESSAGE = "Employee with ID 1 not found.";

    /**
     * Prueba unitaria para el constructor que recibe un ID de empleado.
     *
     * Valida que el mensaje se genere correctamente con el ID proporcionado.
     */
    @Test
    void testConstructorWithId() {
        // Act
        EmployeeNotFoundException exception = new EmployeeNotFoundException(EMPLOYEE_ID);

        // Assert
        assertNotNull(exception, "La excepción no debería ser nula");
        assertEquals(EXPECTED_MESSAGE, exception.getMessage(), "El mensaje de error no coincide con el esperado");
    }
}
