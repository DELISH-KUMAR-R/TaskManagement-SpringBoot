package com.Task_1.TaskManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "sub_sub_task")
public class SubSubTask_entity
{
    @Id
    private int sub_sub_task_id;
    @ManyToOne
    @JoinColumn(name = "sub_task_id")
    private SubTask_entity sub_task_id ;
    private String sub_sub_task_name;
    private Date sub_sub_task_deadline;
    private String sub_sub_task_status;
}
