package ma.ibra.taskmangerprobackend.Domain.dto;

import lombok.*;
import ma.ibra.taskmangerprobackend.Domain.enums.UserRole;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}
