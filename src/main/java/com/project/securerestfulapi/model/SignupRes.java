package com.project.securerestfulapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRes {
    private Long id;
    private String username;
    private String email;
    private Set<String> role;
}
