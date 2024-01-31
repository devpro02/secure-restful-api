package com.project.securerestfulapi.init;

import com.project.securerestfulapi.entity.ERole;
import com.project.securerestfulapi.entity.Role;
import com.project.securerestfulapi.model.ProjectReq;
import com.project.securerestfulapi.model.SignupReq;
import com.project.securerestfulapi.model.TaskReq;
import com.project.securerestfulapi.repository.RoleRepository;
import com.project.securerestfulapi.service.AuthService;
import com.project.securerestfulapi.service.ProjectService;
import com.project.securerestfulapi.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializr implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final AuthService authService;

    public DataInitializr(RoleRepository roleRepository, ProjectService projectService, TaskService taskService, AuthService authService) {
        this.roleRepository = roleRepository;
        this.projectService = projectService;
        this.taskService = taskService;
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Insert role user, admin into roles table
        List<Role> roles = Arrays.asList(new Role(ERole.ROLE_USER), new Role(ERole.ROLE_ADMIN));
        roleRepository.saveAll(roles);

        // Insert user into users table
        SignupReq user = new SignupReq();
        user.setUsername("hoang@actvn");
        user.setEmail("nnh10052002@gmail.com");
        user.setPassword("12345");
        Set<String> rolesUser = new HashSet<>();
        rolesUser.add("user");
        rolesUser.add("admin");
        user.setRole(rolesUser);
        authService.registerUser(user);

        // Insert projects
        List<ProjectReq> projects = Arrays.asList(
                new ProjectReq("E-commerce Platform", "Develop an online e-commerce platform with payment integration", "John Doe"),
                new ProjectReq("Mobile App Development", "Create a cross-platform mobile application for social networking", "Jane Smith")
        );
        projects.forEach(projectService::createProject);

        // Insert tasks
        List<TaskReq> tasks_1 = Arrays.asList(
                new TaskReq("Set up Payment Gateway", "Alice Johnson"),
                new TaskReq("Design User Interface", "Bob Smith"),
                new TaskReq("Perform Quality Assurance Testing", "Eva Williams"),
                new TaskReq("Develop Product Search Feature", "Alice Johnson")
        );
        tasks_1.forEach(t -> taskService.createTask(1L, t));
        List<TaskReq> tasks_2 = Arrays.asList(
                new TaskReq("Implement User Authentication", "Charlie Brown"),
                new TaskReq("Design Social Feed Interface", "David Taylor")
        );
        tasks_2.forEach(t-> taskService.createTask(2L, t));
    }
}
