package ma.ibra.taskmangerprobackend.Business.employee;

import ma.ibra.taskmangerprobackend.Domain.dto.TaskDto;

import java.util.List;

public interface IEmployeeService {
    List<TaskDto> getTasksByUserId();
}
