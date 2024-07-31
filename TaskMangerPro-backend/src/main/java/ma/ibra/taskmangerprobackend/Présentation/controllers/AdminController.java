package ma.ibra.taskmangerprobackend.Présentation.controllers;

import ma.ibra.taskmangerprobackend.Business.admin.IAdminService;
import ma.ibra.taskmangerprobackend.Domain.dto.CommentDto;
import ma.ibra.taskmangerprobackend.Domain.dto.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
    private final IAdminService _adminService;

    public AdminController(IAdminService _adminService) {
        this._adminService = _adminService;
    }
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(_adminService.getUsers());
    }
    @PostMapping("/task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto result = _adminService.createTask(taskDto);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok(_adminService.getAllTasks());
    }
    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        _adminService.deleteTask(id);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/getTask/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        TaskDto result = _adminService.getTaskById(id);
        return ResponseEntity.ok().body(result);
    }
    @PutMapping("/updateTask/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        TaskDto result = _adminService.updateTask(id, taskDto);
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(result);
    }
    @GetMapping("/searchTask/{title}")
    public ResponseEntity<List<TaskDto>> searchTask(@PathVariable String title) {
        List<TaskDto> result = _adminService.searchTaskByTitle(title);
        return ResponseEntity.ok().body(result);
    }
    @PostMapping("/createComment/{taskId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long taskId, @RequestParam String content) {
        CommentDto result = _adminService.createComment(taskId, content);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping("/taskComments/{taskId}")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long taskId) {
        List<CommentDto> result = _adminService.getCommentsByTask(taskId);
        return ResponseEntity.ok().body(result);
    }
}
