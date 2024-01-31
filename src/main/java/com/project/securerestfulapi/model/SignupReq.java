package com.project.securerestfulapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupReq {
    private String username;
    private String email;
    private String password;
    private Set<String> role;
}
