package mx.example.proyecto_hexagonal.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Employee List Request DTO for bulk creation of employees")
public class EmployeeListRequest {

    @Schema(description = "List of employees to be created")
    private List<EmployeeRequest> employees;
}
