package ma.ibra.taskmangerprobackend.Business.auth;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ma.ibra.taskmangerprobackend.Domain.dto.UserDto;
import ma.ibra.taskmangerprobackend.Domain.entities.User;
import ma.ibra.taskmangerprobackend.Domain.enums.UserRole;
import ma.ibra.taskmangerprobackend.Domain.inputs.SignuoRequest;
import ma.ibra.taskmangerprobackend.Domain.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    private final UserRepository _userRepository;
    @PostConstruct
    public void createAdminAccount() {
        Optional<User> optionalUser = _userRepository.findByUserRole(UserRole.ADMIN);
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            _userRepository.save(user);
            System.out.println("Admin account created successfully");
        } else {
            System.out.println("Admin account already exist!");
        }
    }

    @Override
    public UserDto signupUser(SignuoRequest signuoRequest) {
        User user = new User();
        user.setEmail(signuoRequest.getEmail());
        user.setName(signuoRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signuoRequest.getPassword()));
        user.setUserRole(UserRole.EMPLOYEE);
        User createdUser = _userRepository.save(user);
        return createdUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return _userRepository.findFirstByEmail(email).isPresent();
    }
}
