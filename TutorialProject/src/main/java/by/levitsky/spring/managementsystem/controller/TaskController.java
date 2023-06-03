package by.levitsky.spring.managementsystem.controller;

import by.levitsky.spring.managementsystem.dto.TaskDto;
import by.levitsky.spring.managementsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/employee/{employeeId}/tasks")
    public ResponseEntity<TaskDto> saveTask(@PathVariable(value = "employeeId") long employeeId,
                                            @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.addTask(taskDto, employeeId), HttpStatus.CREATED);
    }

    @GetMapping("/employee/{employeeId}/tasks")
    public List<TaskDto> getTasksByEmployeeId(@PathVariable(value = "employeeId") long employeeId) {
        return taskService.getAllTasksByEmployeeId(employeeId);
    }

    @GetMapping("/employee/{employeeId}/tasks/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable(value = "employeeId") long employeeId,
                                               @PathVariable(value = "id") long taskId) {
        TaskDto taskDto = taskService.getTaskById(taskId, employeeId);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    @PutMapping("/employee/{employeeId}/tasks/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable(value = "employeeId") long employeeId,
                                              @PathVariable(value = "id") long taskId,
                                              @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(employeeId, taskId, taskDto), HttpStatus.OK);
    }

    @DeleteMapping("/employee/{employeeId}/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(value = "employeeId") long employeeId,
                                             @PathVariable(value = "id") long taskId) {
        taskService.deleteTask(taskId, employeeId);
        return new ResponseEntity<>("Task deleted successfully!", HttpStatus.OK);
    }
}
