package mx.example.proyecto_hexagonal.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import mx.example.proyecto_hexagonal.application.dto.EmployeeRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeResponse;
import mx.example.proyecto_hexagonal.application.exceptions.custom.DatabaseConnectionException;
import mx.example.proyecto_hexagonal.application.exceptions.custom.EmployeeNotFoundException;
import mx.example.proyecto_hexagonal.application.mapper.EmployeeMapper;
import mx.example.proyecto_hexagonal.application.port.EmployeeServicePort;
import mx.example.proyecto_hexagonal.domain.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeServicePort {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        try {
            return employeeMapper.toResponseList(employeeRepository.findAll());
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error connecting to the database", e);
        }
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toResponse)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        try {
            Employee employee = employeeMapper.toEntity(request);
            Employee savedEmployee = employeeRepository.save(employee);
            return employeeMapper.toResponse(savedEmployee);
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error saving to the database", e);
        }
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        Employee updatedEmployee = employeeMapper.toEntity(request);
        updatedEmployee.setId(existingEmployee.getId());

        try {
            Employee savedEmployee = employeeRepository.save(updatedEmployee);
            return employeeMapper.toResponse(savedEmployee);
        } catch (Exception e) {
            throw new DatabaseConnectionException("Error updating database", e);
        }
    }

    @Override
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        } else {
            throw new EmployeeNotFoundException(id);
        }
    }

    @Override
    public List<EmployeeResponse> findEmployeesByPosition(String position) {
        return employeeMapper.toResponseList(employeeRepository.findByPosition(position));
    }
}
