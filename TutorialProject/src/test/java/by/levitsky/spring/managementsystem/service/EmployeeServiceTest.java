package by.levitsky.spring.managementsystem.service;

import by.levitsky.spring.managementsystem.dto.EmployeeDto;
import by.levitsky.spring.managementsystem.dto.EmployeeResponse;
import by.levitsky.spring.managementsystem.entity.Employee;
import by.levitsky.spring.managementsystem.repository.EmployeeRepository;
import by.levitsky.spring.managementsystem.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void EmployeeService_CreateEmployee_ReturnsEmployeeDto() {
        Employee employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        EmployeeDto employeeDto = EmployeeDto
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenReturn(employee);

        EmployeeDto savedEmployee = employeeService.addEmployee(employeeDto);
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void EmployeeService_GetAll_ReturnsResponseDto() {
        Page<Employee> employees = Mockito.mock(Page.class);

        when(employeeRepository.findAll(
                Mockito.any(Pageable.class)))
                .thenReturn(employees);

        EmployeeResponse saveEmployees = employeeService.getAllEmployees(1, 10);

        assertThat(saveEmployees).isNotNull();
    }

    @Test
    public void EmployeeService_GetEmployeeById_ReturnsEmployeeDto() {
        Employee employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        when(employeeRepository.findById(Integer.toUnsignedLong(1)))
                .thenReturn(Optional.ofNullable(employee));

        EmployeeDto savedEmployee = employeeService.getEmployeeById(1);
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void EmployeeService_UpdateEmployee_ReturnsEmployeeDto() {
        Employee employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        EmployeeDto employeeDto = EmployeeDto
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        when(employeeRepository.findById(Integer.toUnsignedLong(1)))
                .thenReturn(Optional.ofNullable(employee));

        when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenReturn(employee);

        EmployeeDto savedEmployee = employeeService.updateEmployee(1, employeeDto);

        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void EmployeeService_DeleteEmployeeById_ReturnsEmployeeDto() {
        Employee employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        when(employeeRepository
                .findById(Integer.toUnsignedLong(1)))
                .thenReturn(Optional.ofNullable(employee));

        assertAll(() -> employeeService.deleteEmployee(1));
    }
}
