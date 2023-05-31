package by.levitsky.spring.managementsystem.service;

import by.levitsky.spring.managementsystem.dto.EmployeeDto;
import by.levitsky.spring.managementsystem.entity.Employee;
import by.levitsky.spring.managementsystem.exception.ResourceNotFoundException;
import by.levitsky.spring.managementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.
                findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "ID", id));
    }

    @Override
    public Employee updateEmployee(long id, Employee employee) {
        Employee employeeToUpd = employeeRepository.
                findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "ID", id));

        employeeToUpd.setFirst_name(employee.getFirst_name());
        employeeToUpd.setLast_name(employee.getLast_name());
        employeeToUpd.setEmail(employee.getEmail());

        return employeeRepository.save(employeeToUpd);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.
                findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "ID", id));

        employeeRepository.deleteById(id);
    }

    private EmployeeDto mapToDto(Employee employee){
        EmployeeDto employeeDto=new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setFirst_name(employee.getFirst_name());
        employeeDto.setLast_name(employee.getLast_name());
        employeeDto.setEmail(employee.getEmail());

        return employeeDto;
    }

    private Employee mapToEntity(EmployeeDto employeeDto){
        Employee employee=new Employee();

        employee.setId(employeeDto.getId());
        employee.setFirst_name(employeeDto.getFirst_name());
        employee.setLast_name(employeeDto.getLast_name());
        employee.setEmail(employeeDto.getEmail());

        return employee;
    }
}
