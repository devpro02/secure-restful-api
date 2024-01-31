package com.project.securerestfulapi.service;

import com.project.securerestfulapi.entity.Project;
import com.project.securerestfulapi.exception.EntityNotFoundException;
import com.project.securerestfulapi.mapper.ProjectMapper;
import com.project.securerestfulapi.model.MessageResponse;
import com.project.securerestfulapi.model.ProjectReq;
import com.project.securerestfulapi.model.ProjectRes;
import com.project.securerestfulapi.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Calendar;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectRes createProject(@RequestBody ProjectReq projectReq) {
        Project project = projectRepository.save(projectMapper.mapProjectFromProjectReq(projectReq));
        return projectMapper.mapProjectResFromProject(project);
    }
    public List<ProjectRes> getAllProjects() {
        return projectMapper.mapListProjectResFromListProject(projectRepository.findAll());
    }
    public ProjectRes getProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project not found!"));
        return projectMapper.mapProjectResFromProject(project);
    }
    public ProjectRes updateProject(Long id, ProjectReq projectReq) {
        Project project = projectMapper.mapProjectFromProjectReq(id, projectReq);
        return projectMapper.mapProjectResFromProject(projectRepository.save(project));
    }
    public ProjectRes updateEndDate(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project not found"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(project.getEndDate());
        calendar.add(Calendar.MONTH, 5);
        project.setEndDate(calendar.getTime());
        return projectMapper.mapProjectResFromProject(projectRepository.save(project));
    }
    public MessageResponse deleteProject(Long id) {
        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return new MessageResponse("Project has been deleted!");
        }
        throw new EntityNotFoundException("Project id not found!");
    }

}
