package by.levitsky.spring.managementsystem.service;

import by.levitsky.spring.managementsystem.dto.TaskDto;
import by.levitsky.spring.managementsystem.entity.Employee;
import by.levitsky.spring.managementsystem.entity.Task;
import by.levitsky.spring.managementsystem.exception.ResourceNotFoundException;
import by.levitsky.spring.managementsystem.repository.EmployeeRepository;
import by.levitsky.spring.managementsystem.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public TaskDto addTask(TaskDto taskDto, long employeeId) {
        Task task = mapToEntity(taskDto);
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "ID", employeeId));

        task.setEmployee(employee);
        Task newTask = taskRepository.save(task);

        return mapToDto(newTask);
    }

    @Override
    public List<TaskDto> getAllTasksByEmployeeId(long employeeId) {
        List<Task> tasks = taskRepository.findByEmployeeId(employeeId);

        return tasks.stream().map(task -> mapToDto(task))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskById(long taskId, long employeeId) {
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "ID", employeeId));
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task", "ID", taskId));

        if (task.getEmployee().getId() != employee.getId()) {
            throw new ResourceNotFoundException("Task", "ID", taskId);
        }

        return mapToDto(task);
    }

    @Override
    public TaskDto updateTask(long employeeId, long taskId, TaskDto taskDto) {
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "ID", employeeId));

        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task", "ID", taskId));

        if (task.getEmployee().getId() != employee.getId()) {
            throw new ResourceNotFoundException("Task", "ID", taskId);
        }

        task.setTitle(taskDto.getTitle());
        task.setDueDate(taskDto.getDueDate());
        task.setDescription(taskDto.getDescription());

        Task updTask = taskRepository.save(task);

        return mapToDto(updTask);
    }

    @Override
    public void deleteTask(long taskId, long employeeId) {
        Task task=mapToEntity(getTaskById(taskId,employeeId));

        taskRepository.delete(task);
    }

    private TaskDto mapToDto(Task task) {
        TaskDto taskDto = new TaskDto();

        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDueDate(task.getDueDate());
        taskDto.setDescription(task.getDescription());

        return taskDto;
    }

    private Task mapToEntity(TaskDto taskDto) {
        Task task = new Task();

        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDueDate(taskDto.getDueDate());
        task.setDescription(taskDto.getDescription());

        return task;
    }
}
