package ma.ibra.taskmangerprobackend.Domain.inputs;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}
