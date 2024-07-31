package ma.ibra.taskmangerprobackend.Domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ma.ibra.taskmangerprobackend.Domain.dto.CommentDto;
import ma.ibra.taskmangerprobackend.Domain.dto.UserDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Task task;
    public CommentDto getCommentDto() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(id);
        commentDto.setContent(content);
        commentDto.setCreatedAt(createdAt);
        commentDto.setTaskId(task.getId());
        commentDto.setPostedBy(user.getName());
        return commentDto;
    }
}
