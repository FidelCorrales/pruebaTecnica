package mx.example.proyecto_hexagonal.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(description = "Employee Response DTO containing employee details")
public class EmployeeResponse {

    @Schema(description = "ID of the employee", example = "1")
    private Long id;

    @Schema(description = "First name of the employee", example = "John")
    private String firstName;

    @Schema(description = "Middle name of the employee", example = "Alexander")
    private String middleName;

    @Schema(description = "Last name of the employee", example = "Doe")
    private String lastName;

    @Schema(description = "Second last name of the employee", example = "Smith")
    private String secondLastName;

    @Schema(description = "Age of the employee", example = "30")
    private Integer age;

    @Schema(description = "Gender of the employee", example = "Male")
    private String gender;

    @Schema(description = "Birth date of the employee (yyyy-MM-dd)", example = "1993-05-07")
    private LocalDate birthDate;

    @Schema(description = "Position of the employee", example = "Developer")
    private String position;
}
