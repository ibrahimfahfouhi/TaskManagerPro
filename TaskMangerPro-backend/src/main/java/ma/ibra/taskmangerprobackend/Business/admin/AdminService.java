package ma.ibra.taskmangerprobackend.Business.admin;

import ma.ibra.taskmangerprobackend.Domain.dto.TaskDto;
import ma.ibra.taskmangerprobackend.Domain.dto.UserDto;
import ma.ibra.taskmangerprobackend.Domain.entities.Task;
import ma.ibra.taskmangerprobackend.Domain.entities.User;
import ma.ibra.taskmangerprobackend.Domain.enums.TaskStatus;
import ma.ibra.taskmangerprobackend.Domain.enums.UserRole;
import ma.ibra.taskmangerprobackend.Domain.repositories.TaskRepository;
import ma.ibra.taskmangerprobackend.Domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService{
    private final UserRepository _userRepository;
    private final TaskRepository _taskRepository;

    public AdminService(UserRepository _userRepository, TaskRepository taskRepository) {
        this._userRepository = _userRepository;
        _taskRepository = taskRepository;
    }

    @Override
    public List<UserDto> getUsers() {
        return _userRepository.findAll()
                .stream()
                .filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Optional<User> optionalUser = _userRepository.findById(taskDto.getEmployeeId());
        if (optionalUser.isPresent()) {
            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setDueDate(taskDto.getDueDate());
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
            return _taskRepository.save(task).getTaskDto();
        }
        return null;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return _taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        _taskRepository.deleteById(id);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Optional<Task> optionalTask = _taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDto).orElse(null);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Optional<Task> optionalTask = _taskRepository.findById(id);
        Optional<User> optionalUser = _userRepository.findById(taskDto.getEmployeeId());
        if (optionalTask.isPresent() && optionalUser.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDto.getTitle());
            existingTask.setDescription(taskDto.getDescription());
            existingTask.setDueDate(taskDto.getDueDate());
            existingTask.setPriority(taskDto.getPriority());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDto.getTaskStatus())));
            existingTask.setUser(optionalUser.get());
            return _taskRepository.save(existingTask).getTaskDto();
        }
        return null;
    }

    @Override
    public List<TaskDto> searchTaskByTitle(String title) {
        return _taskRepository.findAllByTitleContaining(title)
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto)
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
