package ma.ibra.taskmangerprobackend.Présentation.controllers;

import ma.ibra.taskmangerprobackend.Business.employee.IEmployeeService;
import ma.ibra.taskmangerprobackend.Domain.dto.CommentDto;
import ma.ibra.taskmangerprobackend.Domain.dto.TaskDto;
import ma.ibra.taskmangerprobackend.Domain.entities.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin("*")
public class EmployeeController {
    private final IEmployeeService _employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        _employeeService = employeeService;
    }
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasksByUserId() {
        List<TaskDto> result = _employeeService.getTasksByUserId();
        return ResponseEntity.ok().body(result);
    }
    @PutMapping("/updateTask/{id}/{status}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @PathVariable String status) {
        TaskDto result = _employeeService.updateTask(id, status);
        if (result == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().body(result);
    }
    @GetMapping("/getTask/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        TaskDto result = _employeeService.getTaskById(id);
        return ResponseEntity.ok().body(result);
    }
    @PostMapping("/createComment/{taskId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long taskId, @RequestParam String content) {
        CommentDto result = _employeeService.createComment(taskId, content);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping("/taskComments/{taskId}")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long taskId) {
        List<CommentDto> result = _employeeService.getCommentsByTask(taskId);
        return ResponseEntity.ok().body(result);
    }
}
