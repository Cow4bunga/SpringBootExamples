package by.levitsky.spring.managementsystem.controller;

import by.levitsky.spring.managementsystem.dto.EmployeeDto;
import by.levitsky.spring.managementsystem.dto.EmployeeResponse;
import by.levitsky.spring.managementsystem.dto.TaskDto;
import by.levitsky.spring.managementsystem.entity.Employee;
import by.levitsky.spring.managementsystem.entity.Task;
import by.levitsky.spring.managementsystem.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;
    private Task task;
    private EmployeeDto employeeDto;
    private TaskDto taskDto;

    @BeforeEach
    public void init() {
        employee = Employee
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        employeeDto = EmployeeDto
                .builder()
                .first_name("Ivan")
                .last_name("Levitsky")
                .email("ivanl@mail.com").build();

        task = Task
                .builder()
                .title("workspace")
                .dueDate("12/10/2023")
                .description("create workspace").build();

        taskDto = TaskDto
                .builder()
                .title("workspace")
                .dueDate("12/10/2023")
                .description("create workspace").build();
    }

    @Test
    public void EmployeeController_CreateEmployee_ReturnCreated() throws Exception {
        given(employeeService.addEmployee(ArgumentMatchers.any()))
                .willAnswer(
                        (invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", CoreMatchers.is(employeeDto.getFirst_name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name", CoreMatchers.is(employeeDto.getLast_name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employeeDto.getEmail())));

    }

    @Test
    public void EmployeeController_GetAllEmployees_ReturnResponseDto() throws Exception {
        EmployeeResponse responseDto = EmployeeResponse.builder()
                .pageSize(10)
                .last(true)
                .pageNo(1)
                .content(Arrays.asList(employeeDto))
                .build();

        when(employeeService.getAllEmployees(1, 10))
                .thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.content.size()",
                                CoreMatchers.is(responseDto.getContent().size())));

    }

    @Test
    public void EmployeeController_GetEmployeeDetails_ReturnEmployeeDto() throws Exception {
        long employeeId = 1;

        when(employeeService.getEmployeeById(employeeId))
                .thenReturn(employeeDto);

        ResultActions response = mockMvc.perform(get("/api/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", CoreMatchers.is(employeeDto.getFirst_name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name", CoreMatchers.is(employeeDto.getLast_name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employeeDto.getEmail())));

    }

    @Test
    public void EmployeeController_UpdateEmployee_ReturnEmployeeDto() throws Exception {
        long employeeId = 1;

        when(employeeService.updateEmployee(employeeId, employeeDto))
                .thenReturn(employeeDto);

        ResultActions response = mockMvc.perform(put("/api/employee/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", CoreMatchers.is(employeeDto.getFirst_name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name", CoreMatchers.is(employeeDto.getLast_name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employeeDto.getEmail())));

    }

    @Test
    public void EmployeeController_DeleteEmployee_ReturnString() throws Exception {
        long employeeId = 1;
        doNothing().when(employeeService).deleteEmployee(employeeId);

        when(employeeService.updateEmployee(employeeId, employeeDto))
                .thenReturn(employeeDto);

        ResultActions response = mockMvc.perform(delete("/api/employee/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
