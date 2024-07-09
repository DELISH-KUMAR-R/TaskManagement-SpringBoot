package com.Task_1.TaskManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "sub_task")
public class SubTask_entity
{
        @Id
        private int sub_task_id;

        @ManyToOne
        @JoinColumn(name = "task_id")
        private Task_entity task_id ;
        private String sub_task_name;
        private Date sub_task_deadline;
        private String sub_task_status;
}
