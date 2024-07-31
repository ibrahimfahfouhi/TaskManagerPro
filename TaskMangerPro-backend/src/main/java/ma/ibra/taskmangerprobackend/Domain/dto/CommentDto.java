package ma.ibra.taskmangerprobackend.Domain.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class CommentDto {
    private Long id;
    private String content;
    private LocalDate createdAt;
    private Long taskId;
    private Long userId;
    private String postedBy;
}
