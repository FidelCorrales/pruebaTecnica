package mx.example.proyecto_hexagonal.infrastructure.adapter.persistence;

import mx.example.proyecto_hexagonal.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find all employees by their position.
     *
     * @param position the position to filter
     * @return list of employees with the specified position
     */
    List<Employee> findByPosition(String position);
}
