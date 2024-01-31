package com.project.securerestfulapi.validator;
import com.project.securerestfulapi.entity.User;
import com.project.securerestfulapi.exception.BadRequestException;
import com.project.securerestfulapi.exception.EntityNotFoundException;
import com.project.securerestfulapi.model.LoginReq;
import com.project.securerestfulapi.model.SignupReq;
import com.project.securerestfulapi.repository.RoleRepository;
import com.project.securerestfulapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthValidator {
    private static final String USERNAME_DOES_NOT_EXIST = "Username does not exist!";
    private static final String USERNAME_ALREADY_IN_DB = "Username already in database! Choose another";
    private static final String EMAIL_ALREADY_IN_DB = "Email already in database! Choose another";
    private static final String PASSWORD_NOT_VALID = "Password not valid!";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateLogin(LoginReq loginReq) {
        checkUsernameExisted(loginReq.getUsername());
        User user = userRepository.findByUsername(loginReq.getUsername()).get();
        if(passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {return;}
        throw new BadRequestException(PASSWORD_NOT_VALID);
    }
    public void validateRegister(SignupReq signupReq) {
        if(userRepository.existsByUsername(signupReq.getUsername())) {
            throw new BadRequestException(USERNAME_ALREADY_IN_DB);
        }
        if (userRepository.existsByEmail(signupReq.getEmail())) {
            throw new BadRequestException(EMAIL_ALREADY_IN_DB);
        }
    }
    public void checkUsernameExisted(String username) {
        if(userRepository.existsByUsername(username)) {return ;}
        throw new EntityNotFoundException(USERNAME_DOES_NOT_EXIST);
    }
    public void checkRoleExisted(String role) {
        if(role.equals("admin") || role.equals("user")) {return;}
        throw new EntityNotFoundException("Role " + role + " not found!");
    }
}
