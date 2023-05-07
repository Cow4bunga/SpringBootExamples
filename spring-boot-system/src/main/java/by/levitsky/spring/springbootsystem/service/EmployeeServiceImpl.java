package by.levitsky.spring.springbootsystem.service;

import by.levitsky.spring.springbootsystem.model.Employee;
import by.levitsky.spring.springbootsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void create(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public boolean updateEmployee(long id, Employee employee) {
        if(employeeRepository.existsById(id)){
            Employee employeeToUpd=employeeRepository.findById(id).get();

            employeeToUpd.setFirst_name(employee.getFirst_name());
            employeeToUpd.setLast_name(employee.getLast_name());
            employeeToUpd.setAge(employee.getAge());
            employeeToUpd.setEmail(employee.getEmail());
            employeeToUpd.setEmail(employee.getPosition());

            employeeRepository.save(employeeToUpd);
        }
        return false;
    }

    @Override
    public void deleteById(long id) {
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
        }
    }
}
