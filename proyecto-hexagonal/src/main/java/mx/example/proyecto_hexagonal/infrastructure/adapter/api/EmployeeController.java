package mx.example.proyecto_hexagonal.infrastructure.adapter.api;

import mx.example.proyecto_hexagonal.application.dto.EmployeeListRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeResponse;
import mx.example.proyecto_hexagonal.application.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Management", description = "CRUD operations for Employee management")
@RequiredArgsConstructor
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @Operation(summary = "Get all employees", description = "Retrieve a list of all registered employees")
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        logger.info("Start get All Employees process");
        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        logger.info("Finish get All Employees process");
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Get employee by ID", description = "Retrieve a single employee by his ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        logger.info("Start get employee by ID process");
        EmployeeResponse response = employeeService.getEmployeeById(id);
        logger.info("Finish get employee by ID process");
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create one or multiple employees", description = "Insert one or more employees in a single request")
    @PostMapping
    public ResponseEntity<List<EmployeeResponse>> createEmployees(@RequestBody EmployeeListRequest request) {
        logger.info("Start Create one or multiple employees process");
        List<EmployeeResponse> responses = employeeService.createEmployees(request);
        logger.info("Finish Create one or multiple employees process");
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Update an existing employee", description = "Update employee details by ID")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        logger.info("Start Update an existing employee process");
        EmployeeResponse response = employeeService.updateEmployee(id, request);
        logger.info("Finish Update an existing employee process");
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete an employee by ID", description = "Remove an employee from the database by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        logger.info("Start Delete an employee with ID: {} process", id);
        if (employeeService.deleteEmployee(id)) {
            logger.info("Finish Delete an employee with ID: {} process", id);
            return ResponseEntity.noContent().build();
        }

        logger.warn("Employee with ID {} not found for deletion", id);
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Find employees by position", description = "Retrieve a list of employees with the specified position")
    @GetMapping("/position/{position}")
    public ResponseEntity<List<EmployeeResponse>> findEmployeesByPosition(@PathVariable String position) {
        List<EmployeeResponse> employees = employeeService.findEmployeesByPosition(position);
        return employees.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(employees);
    }
}
