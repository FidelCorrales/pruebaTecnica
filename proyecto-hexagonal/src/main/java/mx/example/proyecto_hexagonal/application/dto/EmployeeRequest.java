package mx.example.proyecto_hexagonal.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(description = "Employee Request DTO for creating or updating an employee")
public class EmployeeRequest {

    @Schema(description = "First name of the employee", example = "Fidel", minLength = 1, maxLength = 100)
    @NotBlank(message = "First name is required")
    @Size(min = 1,max = 100, message = "First name must be at most 100 characters")
    private String firstName;

    @Schema(description = "Middle name of the employee", example = "Francisco")
    private String middleName;

    @Schema(description = "Last name of the employee", example = "Corrales", minLength = 1, maxLength = 100)
    @NotBlank(message = "Last name is required")
    @Size(min = 1,max = 100, message = "First name must be at most 100 characters")
    private String lastName;

    @Schema(description = "Second last name of the employee", example = "Andrade")
    private String secondLastName;

    @Schema(description = "Gender of the employee", example = "Male")
    @NotNull(message = "Gender is required")
    private String gender;

    @Schema(description = "Birth date of the employee (yyyy-MM-dd)", example = "1995-05-07")
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @Schema(description = "Position of the employee", example = "Developer")
    @NotNull(message = "Position is required")
    private String position;

}

