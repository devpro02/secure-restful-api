package com.project.securerestfulapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String implementer;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Task(String title, String implementer, TaskStatus status, Date deadline) {
        this.title = title;
        this.implementer = implementer;
        this.status = status;
        this.deadline = deadline;
    }
}
