package by.levitsky.spring.managementsystem.repository;

import by.levitsky.spring.managementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
