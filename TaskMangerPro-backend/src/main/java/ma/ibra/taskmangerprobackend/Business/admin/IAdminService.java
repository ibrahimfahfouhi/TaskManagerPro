package ma.ibra.taskmangerprobackend.Business.admin;
import ma.ibra.taskmangerprobackend.Domain.dto.TaskDto;
import ma.ibra.taskmangerprobackend.Domain.dto.UserDto;

import java.util.List;

public interface IAdminService {
    List<UserDto> getUsers();
    TaskDto createTask(TaskDto taskDto);
    List<TaskDto> getAllTasks();
    void deleteTask(Long id);
    TaskDto getTaskById(Long id);
    TaskDto updateTask(Long id, TaskDto taskDto);
    List<TaskDto> searchTaskByTitle(String title);
}
