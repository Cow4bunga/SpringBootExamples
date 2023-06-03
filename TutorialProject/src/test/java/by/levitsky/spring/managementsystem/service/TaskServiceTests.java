package by.levitsky.spring.managementsystem.service;

import by.levitsky.spring.managementsystem.dto.EmployeeDto;
import by.levitsky.spring.managementsystem.dto.TaskDto;
import by.levitsky.spring.managementsystem.entity.Employee;
import by.levitsky.spring.managementsystem.entity.Task;
import by.levitsky.spring.managementsystem.repository.EmployeeRepository;
import by.levitsky.spring.managementsystem.repository.TaskRepository;
import by.levitsky.spring.managementsystem.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Employee employee;
    private Task task;
    private EmployeeDto employeeDto;
    private TaskDto taskDto;

    @BeforeEach
    public void init() { // to get rid of repeating code
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
    public void TaskService_CreateTask_ReturnsDto() {
        when(employeeRepository.findById(employee.getId()))
                .thenReturn(Optional.of(employee));

        when(taskRepository.save(Mockito.any(Task.class)))
                .thenReturn(task);

        TaskDto saveTask = taskService.addTask(taskDto, employee.getId());
        assertThat(saveTask).isNotNull();
    }

    @Test
    public void TaskService_FindTasksByEmployeeId_ReturnsTaskDto() {
        long taskID = 1;

        when(taskRepository.findByEmployeeId(taskID)).thenReturn(Arrays.asList(task));

        List<TaskDto> tasksReturned = taskService.getAllTasksByEmployeeId(taskID);
        assertThat(tasksReturned).isNotNull();
    }

    @Test
    public void TaskService_GetTaskById_ReturnsTaskDto() {
        long taskID = 1;
        long employeeId = 1;

        task.setEmployee(employee);

        when(taskRepository.findById(taskID))
                .thenReturn(Optional.of(task));

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));

        TaskDto taskReturned = taskService.getTaskById(taskID, employeeId);
        assertThat(taskReturned).isNotNull();
    }

    @Test
    public void TaskService_UpdateTask_ReturnsTaskDto() {
        long taskID = 1;
        long employeeId = 1;

        employee.setTasks(Arrays.asList(task));
        task.setEmployee(employee);

        when(taskRepository.findById(taskID))
                .thenReturn(Optional.of(task));

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));

        when(taskRepository.save(task))
                .thenReturn(task);

        TaskDto updTask = taskService.updateTask(employeeId, taskID, taskDto);

        assertThat(updTask).isNotNull();
    }

    @Test
    public void TaskService_DeleteTaskById_ReturnsVoid() {
        long taskID = 1;
        long employeeId = 1;

        employee.setTasks(Arrays.asList(task));
        task.setEmployee(employee);

        when(taskRepository.findById(taskID))
                .thenReturn(Optional.of(task));

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));

        assertAll(() -> taskService.deleteTask(taskID, employeeId));
    }
}
