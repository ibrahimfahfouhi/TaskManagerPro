package ma.ibra.taskmangerprobackend.Domain.inputs;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class SignuoRequest {
    private String name;
    private String email;
    private String password;
}
