package com.project.securerestfulapi.model;

import com.project.securerestfulapi.entity.Project;
import com.project.securerestfulapi.entity.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskReq {
    private String title;
    private String implementer;
}
