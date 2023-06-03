package by.levitsky.spring.managementsystem.repository;

import by.levitsky.spring.managementsystem.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void EmployeeRepository_SaveEmployees_ReturnSavedEmployee() {
        // Arrange
        Employee employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        // Act
        Employee saveEmployee = employeeRepository.save(employee);

        // Assert
        assertThat(saveEmployee).isNotNull();
        assertThat(saveEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void EmployeeRepository_GetAll_ReturnMoreThanOne(){
        Employee employeeOne = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        Employee employeeTwo = Employee
                .builder()
                .first_name("Joe")
                .last_name("Mama")
                .email("joem@mail.com").build();

        employeeRepository.save(employeeOne);
        employeeRepository.save(employeeTwo);

        List<Employee> employeeList=employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    public void EmployeeRepository_FindById_ReturnEmployee(){
        Employee employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        employeeRepository.save(employee);
        Employee returnedEmployee=employeeRepository.findById(employee.getId()).get();

        assertThat(returnedEmployee).isNotNull();
    }

    @Test
    public void EmployeeRepository_FindByType_ReturnEmployeeNotNull(){
        Employee employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        employeeRepository.save(employee);
        Employee returnedEmployee=employeeRepository.findByEmail(employee.getEmail()).get();

        assertThat(returnedEmployee).isNotNull();
    }

    @Test
    public void EmployeeRepository_UpdateEmployee_ReturnEmployeeNotNull(){
        Employee employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        employeeRepository.save(employee);

        Employee savedEmployee=employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("lev@mail.com");
        savedEmployee.setFirst_name("John");

        Employee updEmployee=employeeRepository.save(savedEmployee);

        assertThat(updEmployee.getFirst_name()).isNotNull();
        assertThat(updEmployee.getEmail()).isNotNull();
    }

    @Test
    public void EmployeeRepository_DeleteEmployee_ReturnEmployeeIsEmpty(){
        Employee employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        employeeRepository.save(employee);

        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeReturn=employeeRepository.findById(employee.getId());

        assertThat(employeeReturn).isEmpty();
    }
}
