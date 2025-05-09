package mx.example.proyecto_hexagonal.application.mapper;

import lombok.RequiredArgsConstructor;
import mx.example.proyecto_hexagonal.application.dto.EmployeeRequest;
import mx.example.proyecto_hexagonal.application.dto.EmployeeResponse;
import mx.example.proyecto_hexagonal.domain.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final ModelMapper modelMapper;

    public Employee toEntity(EmployeeRequest employeeRequest) {
        return modelMapper.map(employeeRequest, Employee.class);
    }

    public EmployeeResponse toResponse(Employee employee) {
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public List<EmployeeResponse> toResponseList(List<Employee> employees) {
        return employees.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
