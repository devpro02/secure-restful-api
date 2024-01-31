package com.project.securerestfulapi.controller;
import com.project.securerestfulapi.model.MessageResponse;
import com.project.securerestfulapi.model.ProjectReq;
import com.project.securerestfulapi.model.ProjectRes;
import com.project.securerestfulapi.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public ResponseEntity<List<ProjectRes>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ProjectRes> createProject(@RequestBody ProjectReq projectReq) {
        return new ResponseEntity<>(projectService.createProject(projectReq), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProjectRes> getProject(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProject(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProjectRes> updateProject(@PathVariable Long id, @RequestBody ProjectReq projectReq) {
        return new ResponseEntity<>(projectService.updateProject(id, projectReq), HttpStatus.OK);
    }
    @PatchMapping("/{id}/end-date")
    public ResponseEntity<ProjectRes> updateEndDate(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.updateEndDate(id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.deleteProject(id), HttpStatus.OK);
    }
}
