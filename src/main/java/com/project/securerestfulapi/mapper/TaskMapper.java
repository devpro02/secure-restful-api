package com.project.securerestfulapi.mapper;

import com.project.securerestfulapi.entity.Task;
import com.project.securerestfulapi.entity.TaskStatus;
import com.project.securerestfulapi.exception.EntityNotFoundException;
import com.project.securerestfulapi.model.TaskReq;
import com.project.securerestfulapi.model.TaskRes;
import com.project.securerestfulapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskMapper {
    private final TaskRepository taskRepository;

    public TaskMapper(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task mapTaskFromTaskReq (TaskReq from) {
        Task to = new Task();
        to.setTitle(from.getTitle());
        to.setDeadline(new Date());
        to.setImplementer(from.getImplementer());
        to.setStatus(TaskStatus.PROCESSING);
        return to;
    }
    public Task mapTaskFromTaskReq (Long id, TaskReq from) {
        Task to = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        to.setTitle(from.getTitle());
        to.setImplementer(from.getImplementer());
        return to;
    }
    public TaskRes mapTaskResFromTask(Task from) {
        TaskRes to = new TaskRes();
        to.setId(from.getId());
        to.setTitle(from.getTitle());
        to.setImplementer(from.getImplementer());
        to.setStatus(from.getStatus());
        to.setDeadline(from.getDeadline());
        return to;
    }
    public List<TaskRes> mapListTaskResFromListTask(List<Task> from) {
        List<TaskRes> to = new ArrayList<>();
        from.forEach(t -> to.add(mapTaskResFromTask(t)));
        return to;
    }
}
