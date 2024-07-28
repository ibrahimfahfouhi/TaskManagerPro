package ma.ibra.taskmangerprobackend.Présentation.controllers;

import ma.ibra.taskmangerprobackend.Business.employee.IEmployeeService;
import ma.ibra.taskmangerprobackend.Domain.dto.TaskDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
