package mx.example.proyecto_hexagonal.infrastructure.adapter.api;

import mx.example.proyecto_hexagonal.application.dto.EmployeeListRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeResponse;
import mx.example.proyecto_hexagonal.application.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para la clase EmployeeController.
 *
 * Se valida el comportamiento de cada endpoint REST utilizando mocks de los servicios
 * asociados para asegurar que las respuestas HTTP sean las esperadas.
 */
class EmployeeControllerTest {

    // Mock del servicio que maneja la lógica de negocio
    @Mock
    private EmployeeService employeeService;

    // Inyección del controlador con el mock del servicio
    @InjectMocks
    private EmployeeController employeeController;

    /**
     * Configuración inicial antes de cada prueba.
     *
     * Se inicializan los mocks para ser utilizados en los tests.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba unitaria para el endpoint GET /api/v1/employees
     *
     * Valida que el controlador devuelva una lista de empleados y un status 200 (OK).
     */
    @Test
    void testGetAllEmployees() {
        // Arrange
        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("John");
        response.setLastName("Doe");

        when(employeeService.getAllEmployees()).thenReturn(List.of(response));

        // Act
        ResponseEntity<List<EmployeeResponse>> result = employeeController.getAllEmployees();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        verify(employeeService, times(1)).getAllEmployees();
    }

    /**
     * Prueba unitaria para el endpoint GET /api/v1/employees/{id}
     *
     * Valida que el controlador devuelva un empleado cuando el ID existe.
     */
    @Test
    void testGetEmployeeById() {
        // Arrange
        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("Jane");
        response.setLastName("Smith");

        when(employeeService.getEmployeeById(1L)).thenReturn(response);

        // Act
        ResponseEntity<EmployeeResponse> result = employeeController.getEmployeeById(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Jane", result.getBody().getFirstName());
        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    /**
     * Prueba unitaria para el endpoint GET /api/v1/employees/{id}
     *
     * Valida que se retorne un 404 (Not Found) si el ID no existe.
     */
    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeService.getEmployeeById(1L)).thenReturn(null);

        // Act
        ResponseEntity<EmployeeResponse> result = employeeController.getEmployeeById(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    /**
     * Prueba unitaria para el endpoint POST /api/v1/employees
     *
     * Valida que se cree un empleado correctamente y se devuelva un status 200 (OK).
     */
    @Test
    void testCreateEmployees() {
        // Arrange
        EmployeeListRequest request = new EmployeeListRequest();
        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("John");
        response.setLastName("Doe");

        when(employeeService.createEmployees(request)).thenReturn(List.of(response));

        // Act
        ResponseEntity<List<EmployeeResponse>> result = employeeController.createEmployees(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        verify(employeeService, times(1)).createEmployees(request);
    }

    /**
     * Prueba unitaria para el endpoint PUT /api/v1/employees/{id}
     *
     * Valida que un empleado sea actualizado correctamente y se retorne un 200 (OK).
     */
    @Test
    void testUpdateEmployee() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("Jane");
        response.setLastName("Smith");

        when(employeeService.updateEmployee(1L, request)).thenReturn(response);

        // Act
        ResponseEntity<EmployeeResponse> result = employeeController.updateEmployee(1L, request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Jane", result.getBody().getFirstName());
        verify(employeeService, times(1)).updateEmployee(1L, request);
    }

    /**
     * Prueba unitaria para el endpoint PUT /api/v1/employees/{id}
     *
     * Valida que se retorne un 404 (Not Found) si el ID no existe.
     */
    @Test
    void testUpdateEmployee_NotFound() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        when(employeeService.updateEmployee(1L, request)).thenReturn(null);

        // Act
        ResponseEntity<EmployeeResponse> result = employeeController.updateEmployee(1L, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    /**
     * Prueba unitaria para el endpoint DELETE /api/v1/employees/{id}
     *
     * Valida que un empleado sea eliminado correctamente y se retorne un 204 (No Content).
     */
    @Test
    void testDeleteEmployee() {
        // Arrange
        when(employeeService.deleteEmployee(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> result = employeeController.deleteEmployee(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    /**
     * Prueba unitaria para el endpoint DELETE /api/v1/employees/{id}
     *
     * Valida que se retorne un 404 (Not Found) si el ID no existe.
     */
    @Test
    void testDeleteEmployee_NotFound() {
        // Arrange
        when(employeeService.deleteEmployee(1L)).thenReturn(false);

        // Act
        ResponseEntity<Void> result = employeeController.deleteEmployee(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    /**
     * Prueba unitaria para el endpoint GET /api/v1/employees/position/{position}
     *
     * Valida que se devuelvan los empleados con un puesto específico.
     */
    @Test
    void testFindEmployeesByPosition() {
        // Arrange
        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setPosition("Developer");

        when(employeeService.findEmployeesByPosition("Developer")).thenReturn(List.of(response));

        // Act
        ResponseEntity<List<EmployeeResponse>> result = employeeController.findEmployeesByPosition("Developer");

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        verify(employeeService, times(1)).findEmployeesByPosition("Developer");
    }
}
