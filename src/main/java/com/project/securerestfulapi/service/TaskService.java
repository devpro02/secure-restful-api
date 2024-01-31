package com.project.securerestfulapi.service;

import com.project.securerestfulapi.entity.Project;
import com.project.securerestfulapi.entity.Task;
import com.project.securerestfulapi.exception.EntityNotFoundException;
import com.project.securerestfulapi.mapper.TaskMapper;
import com.project.securerestfulapi.model.MessageResponse;
import com.project.securerestfulapi.model.TaskReq;
import com.project.securerestfulapi.model.TaskRes;
import com.project.securerestfulapi.repository.ProjectRepository;
import com.project.securerestfulapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
    }

    public TaskRes createTask(Long projectId, TaskReq taskReq) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found!"));
        Task task = taskMapper.mapTaskFromTaskReq(taskReq);
        task.setProject(project);
        return taskMapper.mapTaskResFromTask(taskRepository.save(task));
    }
    public TaskRes getTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task id not found"));
        return taskMapper.mapTaskResFromTask(task);
    }
    public List<TaskRes> getAllTasks() {
        return taskMapper.mapListTaskResFromListTask(taskRepository.findAll());
    }
    public TaskRes updateTask(Long taskId, TaskReq taskReq) {
        Task task = taskRepository.save(taskRepository.save(taskMapper.mapTaskFromTaskReq(taskId, taskReq)));
        return taskMapper.mapTaskResFromTask(task);
    }
    public TaskRes updateDeadline(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(task.getDeadline());
        calendar.add(Calendar.DATE, 7);
        task.setDeadline(calendar.getTime());
        return taskMapper.mapTaskResFromTask(taskRepository.save(task));
    }
    public MessageResponse deleteTask(Long taskId) {
        if(taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return new MessageResponse("Task has been deleted!");
        }
        throw new EntityNotFoundException("Task id not found");
    }
}
