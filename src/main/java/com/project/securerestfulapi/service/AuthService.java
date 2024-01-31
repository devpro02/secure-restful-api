package com.project.securerestfulapi.service;

import com.project.securerestfulapi.entity.ERole;
import com.project.securerestfulapi.entity.RefreshToken;
import com.project.securerestfulapi.entity.Role;
import com.project.securerestfulapi.entity.User;
import com.project.securerestfulapi.exception.BadRequestException;
import com.project.securerestfulapi.model.*;
import com.project.securerestfulapi.repository.RoleRepository;
import com.project.securerestfulapi.repository.UserRepository;
import com.project.securerestfulapi.security.UserDetailsImpl;
import com.project.securerestfulapi.security.jwt.JwtUtils;
import com.project.securerestfulapi.validator.AuthValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final AuthValidator authValidator;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils, RefreshTokenService refreshTokenService, AuthValidator authValidator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.authValidator = authValidator;
    }

    public JwtRes authenticateUser(LoginReq loginReq) {
        authValidator.validateLogin(loginReq);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return new JwtRes(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    public SignupRes registerUser(SignupReq signUpReq) {
        authValidator.validateRegister(signUpReq);
        User user = new User(signUpReq.getUsername(), signUpReq.getEmail(), encoder.encode(signUpReq.getPassword()));
        Set<String> strRoles = signUpReq.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
            userRole.ifPresent(roles::add);
        } else {
            strRoles.forEach(role -> {
                authValidator.checkRoleExisted(role);
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new BadRequestException("Role admin is not found"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new BadRequestException("Role user is not found"));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        User userEntity = userRepository.save(user);
        return new SignupRes(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail(),strRoles);
    }

    public TokenRefreshRes refreshToken(TokenRefreshReq request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return new TokenRefreshRes(token, requestRefreshToken);
                })
                .orElseThrow(() -> new BadRequestException("Refresh token is not in database!"));
    }

    public MessageResponse logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        refreshTokenService.deleteByUserId(userDetails.getId());
        return new MessageResponse("Log out successful!");
    }
}
