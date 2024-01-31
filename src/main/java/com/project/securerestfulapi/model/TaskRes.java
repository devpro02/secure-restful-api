package com.project.securerestfulapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.securerestfulapi.entity.Project;
import com.project.securerestfulapi.entity.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRes {
    private Long id;
    private String title;
    private String implementer;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date deadline;
}
