package ma.ibra.taskmangerprobackend.Business.employee;

import ma.ibra.taskmangerprobackend.Domain.dto.CommentDto;
import ma.ibra.taskmangerprobackend.Domain.dto.TaskDto;

import java.util.List;

public interface IEmployeeService {
    List<TaskDto> getTasksByUserId();
    TaskDto updateTask(Long id, String status);
    TaskDto getTaskById(Long id);
    CommentDto createComment(Long taskId, String content);
    List<CommentDto> getCommentsByTask(Long taskId);
}
