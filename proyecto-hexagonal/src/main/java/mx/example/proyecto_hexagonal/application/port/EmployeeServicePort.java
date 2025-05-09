package mx.example.proyecto_hexagonal.application.port;

import mx.example.proyecto_hexagonal.application.dto.EmployeeRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeServicePort {

    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse createEmployee(EmployeeRequest request);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);
    boolean deleteEmployee(Long id);
    List<EmployeeResponse> findEmployeesByPosition(String position);
}
