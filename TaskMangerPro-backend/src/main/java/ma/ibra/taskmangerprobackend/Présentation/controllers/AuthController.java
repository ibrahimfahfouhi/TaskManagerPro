package ma.ibra.taskmangerprobackend.Présentation.controllers;

import lombok.RequiredArgsConstructor;
import ma.ibra.taskmangerprobackend.Business.auth.IAuthService;
import ma.ibra.taskmangerprobackend.Business.jwt.IUserService;
import ma.ibra.taskmangerprobackend.Business.utils.JwtUtil;
import ma.ibra.taskmangerprobackend.Domain.dto.AuthenticationResponse;
import ma.ibra.taskmangerprobackend.Domain.dto.UserDto;
import ma.ibra.taskmangerprobackend.Domain.entities.User;
import ma.ibra.taskmangerprobackend.Domain.inputs.AuthenticationRequest;
import ma.ibra.taskmangerprobackend.Domain.inputs.SignuoRequest;
import ma.ibra.taskmangerprobackend.Domain.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService _authService;
    private final UserRepository _userRepository;
    private final JwtUtil jwtUtil;
    private final IUserService _userService;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignuoRequest signuoRequest) {
        if (_authService.hasUserWithEmail(signuoRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exist with this email");
        }
        UserDto createdUserDto = _authService.signupUser(signuoRequest);
        if (createdUserDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        } return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        final UserDetails userDetails = _userService.userDetailService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = _userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return ResponseEntity.ok().body(authenticationResponse);
    }
}
