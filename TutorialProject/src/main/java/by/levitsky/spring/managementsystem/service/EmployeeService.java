package by.levitsky.spring.managementsystem.service;

import by.levitsky.spring.managementsystem.dto.EmployeeDto;
import by.levitsky.spring.managementsystem.dto.EmployeeResponse;
import by.levitsky.spring.managementsystem.entity.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeDto addEmployee(EmployeeDto employeeDto);

    EmployeeResponse getAllEmployees(int pageNo,int pageSize);

    EmployeeDto getEmployeeById(long id);

    EmployeeDto updateEmployee(long id, EmployeeDto employeeDto);

    void deleteEmployee(long id);
}
