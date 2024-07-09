package com.Task_1.TaskManagement.Service;


import com.Task_1.TaskManagement.Entity.SubSubTask_entity;
import com.Task_1.TaskManagement.Entity.SubTask_entity;
import com.Task_1.TaskManagement.Entity.Task_entity;
import com.Task_1.TaskManagement.Repository.SubSubTask_repository;
import com.Task_1.TaskManagement.Repository.SubTask_repository;
import com.Task_1.TaskManagement.Repository.Task_repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class TaskManagementService
{
    @Autowired
    private Task_repository TR;
    @Autowired
    private SubTask_repository STR;
    @Autowired
    private SubSubTask_repository SSTR;

    public List<Task_entity> insertTask(List<Task_entity> task)
    {
        return TR.saveAll(task);
    }

    public List<SubTask_entity> insertSubTask(List<SubTask_entity> task)
    {
        return STR.saveAll(task);
    }


    public List<SubSubTask_entity> insertSubSubTask(List<SubSubTask_entity> task)
    {
        return SSTR.saveAll(task);
    }

    public List<Task_entity> showTask()
    {
        return TR.findAll();
    }

    public List<String> showSubTask(int taskId)
    {
        return TR.showSubTask(taskId);
    }

    public List<String> showSubSubTask(int id1,int id2)
    {
        return TR.showSubSubTask(id1,id2);
    }

    public Task_entity editTask(Task_entity edit)
    {
        Task_entity isExist=TR.findById(edit.getTask_id()).orElse(null);
        if(isExist==null)
        {
            return null;
        }
        isExist.setTask_id(edit.getTask_id());
        isExist.setTask_name(edit.getTask_name());
        isExist.setTask_description(edit.getTask_description());
        isExist.setTask_deadline(edit.getTask_deadline());
        isExist.setTask_status(edit.getTask_status());
        return TR.save(isExist);
    }

    public String editSubTask(int stId, int tId, String stName, Date stDeadline, String stStatus)
    {
        TR.editSubTask(stId,tId,stName,stDeadline,stStatus);
        return "Updated";
    }

    public String editSubSubTask(int tId,int sstId, int stId, String sstName, Date sstDeadline, String sstStatus)
    {
        TR.editSubSubTask(tId,sstId,stId,sstName,sstDeadline,sstStatus);
        return "Updated";
    }

    public String deleteTask(int ids)
    {
        Task_entity isExist=TR.findById(ids).orElse(null);
        TR.deleteById(ids);
        return "Deleted";
    }

    public String deleteSubTask(int tId, int stId)
    {
        TR.deleteTask(tId,stId);
        return "Deleted";
    }

    public String deleteSubSubTask(int stId, int sstId)
    {
        TR.deleteSubSubTask(stId,sstId);
        return "Deleted";
    }


    public List<Task_entity> showDueDatesofTask() {
        List<Task_entity> tasks = TR.findAll();

        // Get today's date
        LocalDate today = LocalDate.now();

        // Filter tasks where task_deadline is before or equal to today's date
        List<Task_entity> overdueTasks = tasks.stream()
                .filter(task -> toLocalDate(task.getTask_deadline()).isBefore(today) || toLocalDate(task.getTask_deadline()).isEqual(today))
                .collect(Collectors.toList());

        System.out.println(overdueTasks);
        return overdueTasks;
    }

    private LocalDate toLocalDate(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }
}