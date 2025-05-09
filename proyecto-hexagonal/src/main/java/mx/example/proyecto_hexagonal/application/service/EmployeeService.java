package mx.example.proyecto_hexagonal.application.service;

import lombok.RequiredArgsConstructor;
import mx.example.proyecto_hexagonal.application.dto.EmployeeListRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeResponse;
import mx.example.proyecto_hexagonal.application.mapper.EmployeeMapper;
import mx.example.proyecto_hexagonal.domain.model.Employee;
import mx.example.proyecto_hexagonal.infrastructure.adapter.persistence.EmployeeRepository;
import mx.example.proyecto_hexagonal.shared.utils.AgeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * Retrieve all employees from the database.
     *
     * @return List of EmployeeResponse
     */
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toResponseList(employees);
    }

    /**
     * Retrieve a single employee by its ID.
     *
     * @param id Employee ID
     * @return EmployeeResponse if found, null otherwise
     */
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toResponse)
                .orElse(null);
    }

    /**
     * Create multiple employees from a request.
     *
     * @param request List of employees to create
     * @return List of EmployeeResponse of the created employees
     */
    @Transactional
    public List<EmployeeResponse> createEmployees(EmployeeListRequest request) {
        List<Employee> employees = request.getEmployees().stream()
                .map(employeeDto -> {
                    Employee entity = employeeMapper.toEntity(employeeDto);
                    int edad = AgeUtils.calculateAge(entity.getBirthDate());
                    entity.setAge(edad);
                    return entity;
                })
                .toList();

        List<Employee> savedEmployees = employeeRepository.saveAll(employees);
        return employeeMapper.toResponseList(savedEmployees);
    }

    /**
     * Update an existing employee.
     *
     * @param id      Employee ID
     * @param request EmployeeRequest with new data
     * @return EmployeeResponse with updated data if found, null otherwise
     */
    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    Employee updatedEmployee = employeeMapper.toEntity(request);
                    updatedEmployee.setAge(AgeUtils.calculateAge(request.getBirthDate()));
                    updatedEmployee.setId(existingEmployee.getId());
                    Employee savedEmployee = employeeRepository.save(updatedEmployee);
                    return employeeMapper.toResponse(savedEmployee);
                }).orElse(null);
    }

    /**
     * Delete an employee by its ID.
     *
     * @param id Employee ID
     * @return true if the employee was deleted, false otherwise
     */
    @Transactional
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieve employees by their position.
     *
     * @param position the position to filter by
     * @return List of employees with the specified position
     */
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findEmployeesByPosition(String position) {
        List<Employee> employees = employeeRepository.findByPosition(position);
        return employeeMapper.toResponseList(employees);
    }
}
