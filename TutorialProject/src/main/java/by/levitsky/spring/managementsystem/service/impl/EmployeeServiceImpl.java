package by.levitsky.spring.managementsystem.service.impl;

import by.levitsky.spring.managementsystem.dto.EmployeeDto;
import by.levitsky.spring.managementsystem.dto.EmployeeResponse;
import by.levitsky.spring.managementsystem.entity.Employee;
import by.levitsky.spring.managementsystem.exception.ResourceNotFoundException;
import by.levitsky.spring.managementsystem.repository.EmployeeRepository;
import by.levitsky.spring.managementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirst_name(employeeDto.getFirst_name());
        employee.setLast_name(employeeDto.getLast_name());
        employee.setEmail(employeeDto.getEmail());

        Employee newEmployee = employeeRepository.save(employee);
        EmployeeDto employeeResponse = new EmployeeDto();

        employeeResponse.setId(newEmployee.getId());
        employeeResponse.setFirst_name(newEmployee.getFirst_name());
        employeeResponse.setLast_name(newEmployee.getLast_name());
        employeeResponse.setEmail(newEmployee.getEmail());

        return employeeResponse;
    }

    @Override
    public EmployeeResponse getAllEmployees(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Employee> employees = employeeRepository.findAll(pageable);
        List<Employee> listOfEmployees = employees.getContent();
        List<EmployeeDto> content = listOfEmployees.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setContent(content);
        employeeResponse.setPageNo(employees.getNumber());
        employeeResponse.setPageSize(employees.getSize());
        employeeResponse.setTotalElements(employees.getTotalElements());
        employeeResponse.setTotalPages(employees.getTotalPages());
        employeeResponse.setLast(employees.isLast());

        return employeeResponse;
    }

    @Override
    public EmployeeDto getEmployeeById(long id) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "ID", id));

        return mapToDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "ID", id));

        employee.setFirst_name(employeeDto.getFirst_name());
        employee.setLast_name(employeeDto.getLast_name());
        employee.setEmail(employeeDto.getEmail());

        Employee updEmployee = employeeRepository.save(employee);

        return mapToDto(updEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "ID", id));

        employeeRepository.delete(employee);
    }

    private EmployeeDto mapToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setFirst_name(employee.getFirst_name());
        employeeDto.setLast_name(employee.getLast_name());
        employeeDto.setEmail(employee.getEmail());

        return employeeDto;
    }

    private Employee mapToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();

        employee.setId(employeeDto.getId());
        employee.setFirst_name(employeeDto.getFirst_name());
        employee.setLast_name(employeeDto.getLast_name());
        employee.setEmail(employeeDto.getEmail());

        return employee;
    }
}
