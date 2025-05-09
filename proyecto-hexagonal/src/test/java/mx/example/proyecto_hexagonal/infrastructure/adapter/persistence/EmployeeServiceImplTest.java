package mx.example.proyecto_hexagonal.infrastructure.adapter.persistence;

import mx.example.proyecto_hexagonal.application.dto.EmployeeRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeResponse;
import mx.example.proyecto_hexagonal.application.exceptions.custom.DatabaseConnectionException;
import mx.example.proyecto_hexagonal.application.exceptions.custom.EmployeeNotFoundException;
import mx.example.proyecto_hexagonal.application.mapper.EmployeeMapper;
import mx.example.proyecto_hexagonal.domain.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para la implementación del servicio EmployeeServiceImpl.
 *
 * Se valida el comportamiento del servicio para cada operación CRUD,
 * así como el manejo de excepciones personalizadas.
 */
class EmployeeServiceImplTest {

    // Mock del repositorio que interactúa con la base de datos
    @Mock
    private EmployeeRepository employeeRepository;

    // Mock del mapper que convierte entre entidades y DTOs
    @Mock
    private EmployeeMapper employeeMapper;

    // Inyección de la implementación del servicio con los mocks anteriores
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    /**
     * Inicialización de los mocks antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba unitaria para obtener todos los empleados.
     *
     * Valida que el servicio retorne correctamente una lista de empleados
     * cuando se invoca el método correspondiente.
     */
    @Test
    void testGetAllEmployees_Success() {
        // Arrange
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");

        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("John");
        response.setLastName("Doe");

        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.toResponseList(List.of(employee))).thenReturn(List.of(response));

        // Act
        List<EmployeeResponse> result = employeeService.getAllEmployees();

        // Assert
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    /**
     * Prueba unitaria para el caso en que ocurra un error en la base de datos.
     *
     * Valida que se lance la excepción DatabaseConnectionException cuando el repositorio falla.
     */
    @Test
    void testGetAllEmployees_DatabaseException() {
        // Arrange
        when(employeeRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        DatabaseConnectionException exception = assertThrows(DatabaseConnectionException.class, () -> {
            employeeService.getAllEmployees();
        });

        assertEquals("Error connecting to the database", exception.getMessage());
    }

    /**
     * Prueba unitaria para obtener un empleado por su ID.
     *
     * Valida que el servicio devuelva el empleado si este existe en la base de datos.
     */
    @Test
    void testGetEmployeeById_Success() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);

        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("Jane");
        response.setLastName("Doe");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.toResponse(employee)).thenReturn(response);

        // Act
        EmployeeResponse result = employeeService.getEmployeeById(1L);

        // Assert
        assertEquals("Jane", result.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    /**
     * Prueba unitaria para el caso en que el empleado no exista.
     *
     * Valida que se lance EmployeeNotFoundException si el ID no es encontrado.
     */
    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(1L);
        });
    }

    /**
     * Prueba unitaria para la creación de un nuevo empleado.
     *
     * Valida que el mapeo y el guardado en la base de datos se realicen correctamente.
     */
    @Test
    void testCreateEmployee_Success() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("John");

        Employee employee = new Employee();
        employee.setFirstName("John");

        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("John");

        when(employeeMapper.toEntity(request)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toResponse(employee)).thenReturn(response);

        // Act
        EmployeeResponse result = employeeService.createEmployee(request);

        // Assert
        assertEquals("John", result.getFirstName());
        verify(employeeRepository, times(1)).save(employee);
    }

    /**
     * Prueba unitaria para el manejo de error en la creación de un empleado.
     *
     * Valida que se lance DatabaseConnectionException si ocurre un error en la base de datos.
     */
    @Test
    void testCreateEmployee_DatabaseException() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        Employee employee = new Employee();

        when(employeeMapper.toEntity(request)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenThrow(new RuntimeException("Database Error"));

        // Act & Assert
        assertThrows(DatabaseConnectionException.class, () -> {
            employeeService.createEmployee(request);
        });
    }

    /**
     * Prueba unitaria para actualizar un empleado existente.
     *
     * Valida que los datos se actualicen correctamente en la base de datos.
     */
    @Test
    void testUpdateEmployee_Success() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("John Updated");

        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setFirstName("John Updated");

        EmployeeResponse response = new EmployeeResponse();
        response.setFirstName("John Updated");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeMapper.toEntity(request)).thenReturn(updatedEmployee);
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);
        when(employeeMapper.toResponse(updatedEmployee)).thenReturn(response);

        // Act
        EmployeeResponse result = employeeService.updateEmployee(1L, request);

        // Assert
        assertEquals("John Updated", result.getFirstName());
        verify(employeeRepository, times(1)).save(updatedEmployee);
    }

    /**
     * Prueba unitaria para el caso en que el empleado a actualizar no exista.
     */
    @Test
    void testUpdateEmployee_NotFound() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, request);
        });
    }

    /**
     * Prueba unitaria para eliminar un empleado por su ID.
     *
     * Valida que el método deleteById sea invocado correctamente.
     */
    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        boolean result = employeeService.deleteEmployee(1L);

        assertTrue(result);
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
