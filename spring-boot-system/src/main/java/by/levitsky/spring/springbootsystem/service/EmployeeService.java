package by.levitsky.spring.springbootsystem.service;

import by.levitsky.spring.springbootsystem.model.Employee;

import java.util.List;
import java.util.Optional;

//
public interface EmployeeService {
    void create(Employee employee);
    Iterable<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(long id);
    boolean updateEmployee(long id, Employee employee);
    void deleteById(long id);
}
