package com.project.securerestfulapi.controller;

import com.project.securerestfulapi.entity.Project;
import com.project.securerestfulapi.model.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    @GetMapping()
    public ResponseEntity<MessageResponse> publicContent() {
        return new ResponseEntity<>(new MessageResponse("Get public content successful!"), HttpStatus.OK);
    }
}
