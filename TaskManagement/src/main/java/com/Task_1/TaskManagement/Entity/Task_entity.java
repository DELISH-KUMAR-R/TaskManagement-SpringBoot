package com.Task_1.TaskManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "task")
public class Task_entity
{
        @Id
        private int task_id;
        private String task_name;
        private String task_description;
        private Date task_deadline;
        private String task_status;
}