package com.project.securerestfulapi.controller;
import com.project.securerestfulapi.model.*;
import com.project.securerestfulapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupRes> signup (@RequestBody SignupReq signupReq) {
        return new ResponseEntity<>(authService.registerUser(signupReq), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtRes> login (@RequestBody LoginReq loginReq) {
        return new ResponseEntity<>(authService.authenticateUser(loginReq), HttpStatus.OK);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshRes> refreshToken (@RequestBody TokenRefreshReq tokenRefreshReq) {
        return new ResponseEntity<>(authService.refreshToken(tokenRefreshReq), HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout() {
        return new ResponseEntity<>(authService.logoutUser(), HttpStatus.OK);
    }
}
