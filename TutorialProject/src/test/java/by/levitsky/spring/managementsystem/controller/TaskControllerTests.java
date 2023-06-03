package by.levitsky.spring.managementsystem.controller;

import by.levitsky.spring.managementsystem.dto.EmployeeDto;
import by.levitsky.spring.managementsystem.dto.TaskDto;
import by.levitsky.spring.managementsystem.entity.Employee;
import by.levitsky.spring.managementsystem.entity.Task;
import by.levitsky.spring.managementsystem.service.EmployeeService;
import by.levitsky.spring.managementsystem.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;
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
    public void TaskController_CreateTask_ReturnsTaskDto() throws Exception {
        long employeeId = 1;

        when(taskService.addTask(taskDto, employeeId))
                .thenReturn(taskDto);

        ResultActions response = mockMvc.perform(post("/api/employee/1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(taskDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dueDate", CoreMatchers.is(taskDto.getDueDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(taskDto.getDescription())));

    }

    @Test
    public void TaskController_GetTasksByEmployeeId_ReturnsTaskDto() throws Exception {
        long employeeId = 1;

        when(taskService.getAllTasksByEmployeeId(employeeId))
                .thenReturn(Arrays.asList(taskDto));

        ResultActions response = mockMvc.perform(get("/api/employee/1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(taskDto).size())));
    }

    @Test
    public void TaskController_GetTaskById_ReturnsTaskDto() throws Exception {
        long employeeId = 1;
        long taskId = 1;

        when(taskService.getTaskById(taskId, employeeId))
                .thenReturn(taskDto);

        ResultActions response = mockMvc.perform(get("/api/employee/1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(taskDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dueDate", CoreMatchers.is(taskDto.getDueDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(taskDto.getDescription())));

    }

    @Test
    public void TaskController_UpdateTask_ReturnsTaskDto() throws Exception {
        long employeeId = 1;
        long taskId = 1;

        when(taskService.updateTask(employeeId, taskId, taskDto))
                .thenReturn(taskDto);

        ResultActions response = mockMvc.perform(put("/api/employee/1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(taskDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dueDate", CoreMatchers.is(taskDto.getDueDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(taskDto.getDescription())));

    }

    @Test
    public void TaskController_UpdateTask_ReturnsString() throws Exception {
        long employeeId = 1;
        long taskId = 1;

        doNothing().when(taskService).deleteTask(taskId, employeeId);

        ResultActions response = mockMvc.perform(delete("/api/employee/1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
