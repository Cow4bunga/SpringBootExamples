package by.levitsky.spring.managementsystem.service;

import by.levitsky.spring.managementsystem.dto.TaskDto;
import by.levitsky.spring.managementsystem.entity.Task;

import java.util.List;

public interface TaskService {
    TaskDto addTask(TaskDto taskDto, long employeeId);

    List<TaskDto> getAllTasksByEmployeeId(long employeeId);

    TaskDto getTaskById(long taskId, long employeeId);

    TaskDto updateTask(long employeeId, long taskId, TaskDto taskDto);

    void deleteTask(long taskId, long employeeId);

}
