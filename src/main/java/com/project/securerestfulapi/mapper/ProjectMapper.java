package com.project.securerestfulapi.mapper;

import com.project.securerestfulapi.entity.Project;
import com.project.securerestfulapi.exception.EntityNotFoundException;
import com.project.securerestfulapi.model.ProjectReq;
import com.project.securerestfulapi.model.ProjectRes;
import com.project.securerestfulapi.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProjectMapper {
    private final ProjectRepository projectRepository;

    public ProjectMapper(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project mapProjectFromProjectReq (ProjectReq from) {
        Project to = new Project();
        to.setName(from.getName());
        to.setManager(from.getManager());
        to.setDescription(from.getDescription());
        to.setStartDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(to.getStartDate());
        calendar.add(Calendar.MONTH, 5);
        to.setEndDate(calendar.getTime());
        return to;
    }
    public Project mapProjectFromProjectReq (Long id, ProjectReq from) {
        Project to = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project not found"));
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setManager(from.getManager());
        return to;
    }
    public ProjectRes mapProjectResFromProject(Project from) {
        ProjectRes to = new ProjectRes();
        to.setId(from.getId());
        to.setName(from.getName());
        to.setDescription(from.getDescription());
        to.setManager(from.getManager());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        return to;
    }
    public List<ProjectRes> mapListProjectResFromListProject(List<Project> from) {
        List<ProjectRes> to = new ArrayList<>();
        from.forEach( p -> to.add(mapProjectResFromProject(p)));
        return to;
    }
}
