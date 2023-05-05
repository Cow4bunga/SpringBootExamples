package by.levitsky.spring.managementsystem.service;

import by.levitsky.spring.managementsystem.entity.Employee;
import by.levitsky.spring.managementsystem.repository.EmployeeRepository;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(long id);

    Employee updateEmployee(long id, Employee employee);

    void deleteEmployee(long id);
}
