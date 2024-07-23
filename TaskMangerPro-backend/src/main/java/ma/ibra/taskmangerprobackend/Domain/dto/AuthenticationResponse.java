package ma.ibra.taskmangerprobackend.Domain.dto;

import lombok.*;
import ma.ibra.taskmangerprobackend.Domain.enums.UserRole;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter @Builder
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private UserRole userRole;
}
