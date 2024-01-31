package com.project.securerestfulapi.controller;
import com.project.securerestfulapi.model.MessageResponse;
import com.project.securerestfulapi.model.TaskReq;
import com.project.securerestfulapi.model.TaskRes;
import com.project.securerestfulapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<TaskRes> createTask(@PathVariable(name = "projectId") Long projectId, @RequestBody TaskReq taskReq) {
        return new ResponseEntity<>(taskService.createTask(projectId, taskReq), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<TaskRes>> getAllTasks () {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskRes> getTask(@PathVariable(name = "taskId") Long taskId) {
        return new ResponseEntity<>(taskService.getTask(taskId),HttpStatus.OK);
    }
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskRes> updateTask(@PathVariable(name = "taskId") Long taskId, @RequestBody TaskReq taskReq) {
        return new ResponseEntity<>(taskService.updateTask(taskId, taskReq),HttpStatus.OK);
    }
    @PatchMapping("/{taskId}/deadline")
    public ResponseEntity<TaskRes> updateDeadline(@PathVariable(name = "taskId") Long taskId) {
        return new ResponseEntity<>(taskService.updateDeadline(taskId), HttpStatus.OK);
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<MessageResponse> deleteTask(@PathVariable(name = "taskId") Long taskId) {
        return new ResponseEntity<>(taskService.deleteTask(taskId), HttpStatus.OK);
    }
}
