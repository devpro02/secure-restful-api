package com.project.securerestfulapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    private String code;
    private String description;
    private String reasonCode;
}

