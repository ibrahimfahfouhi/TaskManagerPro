package ma.ibra.taskmangerprobackend.Domain.dto;

import lombok.*;
import ma.ibra.taskmangerprobackend.Domain.enums.TaskStatus;

import java.sql.Date;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private TaskStatus taskStatus;
    private Long employeeId;
    private String employeeName;

}
