package ma.ibra.taskmangerprobackend.Business.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService {
    UserDetailsService userDetailService();
}
