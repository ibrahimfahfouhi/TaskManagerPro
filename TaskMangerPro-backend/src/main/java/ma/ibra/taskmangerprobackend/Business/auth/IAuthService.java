package ma.ibra.taskmangerprobackend.Business.auth;

import ma.ibra.taskmangerprobackend.Domain.dto.UserDto;
import ma.ibra.taskmangerprobackend.Domain.inputs.SignuoRequest;

public interface IAuthService {
    UserDto signupUser(SignuoRequest signuoRequest);
    boolean hasUserWithEmail(String email);
}
