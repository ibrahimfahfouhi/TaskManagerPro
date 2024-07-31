package ma.ibra.taskmangerprobackend.Business.employee;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.ibra.taskmangerprobackend.Business.utils.JwtUtil;
import ma.ibra.taskmangerprobackend.Domain.dto.CommentDto;
import ma.ibra.taskmangerprobackend.Domain.dto.TaskDto;
import ma.ibra.taskmangerprobackend.Domain.entities.Comment;
import ma.ibra.taskmangerprobackend.Domain.entities.Task;
import ma.ibra.taskmangerprobackend.Domain.entities.User;
import ma.ibra.taskmangerprobackend.Domain.enums.TaskStatus;
import ma.ibra.taskmangerprobackend.Domain.repositories.CommentRepository;
import ma.ibra.taskmangerprobackend.Domain.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{
    private final TaskRepository _taskRepository;
    private final CommentRepository _commentRepository;
    private final JwtUtil jwtUtil;

    @Override
    public List<TaskDto> getTasksByUserId() {
        User user = jwtUtil.getLoggedInUser();
        if (user != null) {
            return _taskRepository.findAllByUserId(user.getId())
                    .stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDto)
                    .collect(Collectors.toList());
        }
        throw new EntityNotFoundException("User not found");
    }

    @Override
    public TaskDto updateTask(Long id, String status) {
        Optional<Task> optionalTask = _taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setTaskStatus(mapStringToTaskStatus(status));
            return _taskRepository.save(existingTask).getTaskDto();
        }
        throw new EntityNotFoundException("Task not found");
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Optional<Task> optionalTask = _taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDto).orElse(null);
    }

    @Override
    public CommentDto createComment(Long taskId, String content) {
        Optional<Task> optionalTask = _taskRepository.findById(taskId);
        User user = jwtUtil.getLoggedInUser();
        if (optionalTask.isPresent() && user != null) {
            Comment comment = new Comment();
            comment.setCreatedAt(LocalDate.now());
            comment.setContent(content);
            comment.setTask(optionalTask.get());
            comment.setUser(user);
            _commentRepository.save(comment).getCommentDto();
        }
        throw new EntityNotFoundException("User or Task not found");
    }

    @Override
    public List<CommentDto> getCommentsByTask(Long taskId) {
        return _commentRepository.findAllByTaskId(taskId)
                .stream()
                .map(Comment::getCommentDto)
                .collect(Collectors.toList());
    }

    private TaskStatus mapStringToTaskStatus(String status) {
        return switch (status) {
            case "PENDING" -> TaskStatus.PENDING;
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;
        };
    }
}
