package ma.ibra.taskmangerprobackend.Domain.repositories;

import ma.ibra.taskmangerprobackend.Domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTaskId(Long taskId);
}
