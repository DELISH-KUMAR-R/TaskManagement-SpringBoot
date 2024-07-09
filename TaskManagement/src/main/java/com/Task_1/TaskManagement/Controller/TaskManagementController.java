package com.Task_1.TaskManagement.Controller;

import com.Task_1.TaskManagement.Entity.SubSubTask_entity;
import com.Task_1.TaskManagement.Entity.SubTask_entity;
import com.Task_1.TaskManagement.Entity.Task_entity;
import com.Task_1.TaskManagement.Service.TaskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/root")
@EnableMethodSecurity(prePostEnabled = true)
public class TaskManagementController
{
    @Autowired
    private TaskManagementService TMS;
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/")
    public String index()
    {
        return "WELCOME TO TASK MANAGEMENT SYSTEM<br><br>" +
                "/task  [create a new task]<br><br>" +
                "/subTask [create a new sub task with reference of task]<br><br>" +
                "/subSubTask [create a new sub-sub task with reference of sub task and task]<br><br>" +
                "/showTasks [for display all task along with sub task and sub sub task]<br><br>"
                ;

    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/task") //insert the task by user
    public List<Task_entity> insertTask(@RequestBody List<Task_entity> task)
    {
        return TMS.insertTask(task);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/subTask")
    public List<SubTask_entity> insertSubTask(@RequestBody List<SubTask_entity> task)
    {
        return TMS.insertSubTask(task);
    }


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping("/subSubTask")
    public List<SubSubTask_entity> insertSubSubTask(@RequestBody List<SubSubTask_entity> task)
    {
        return TMS.insertSubSubTask(task);
    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/showTask")
    public List<Task_entity> showTask()
    {
        System.out.println("authenticated");
        return TMS.showTask();
    }


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/showSubTask")
    public List<String> showSubTask(@RequestParam(value = "id") int taskId)
    {
        return TMS.showSubTask(taskId);
    }


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/showSubSubTask")
    public List<String> showSubSubTask(
            @RequestParam(value = "id1") int id1,
            @RequestParam(value = "id2") int id2
    )
    {
        return TMS.showSubSubTask(id1,id2);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/editTask")
    public Task_entity editTask(@RequestBody Task_entity edit)
    {
        return TMS.editTask(edit);
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/editSubTask")
    public String editSubTask(
            @RequestParam(value = "stId") int stId,
            @RequestParam(value = "tId") int tId,
            @RequestParam(value = "stName") String stName,
            @RequestParam(value = "stDeadline")@DateTimeFormat(pattern = "yyyy-MM-dd")  Date stDeadline,
            @RequestParam(value = "stStatus") String stStatus
            )
    {
        return TMS.editSubTask(stId,tId,stName,stDeadline,stStatus);
    }
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/editSubSubTask")
    public String editSubSubTask(
            @RequestParam(value = "tId") int tId,
            @RequestParam(value = "sstId") int sstId,
            @RequestParam(value = "stId") int stId,
            @RequestParam(value = "sstName") String sstName,
            @RequestParam(value = "sstDeadline")@DateTimeFormat(pattern = "yyyy-MM-dd") Date sstDeadline,
            @RequestParam(value = "sstStatus") String sstStatus
    )
    {
        return TMS.editSubSubTask(tId,sstId,stId,sstName,sstDeadline,sstStatus);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @DeleteMapping("/deleteTask")
    public String deleteTask(@RequestParam(value = "tid") int ids)
    {
        return TMS.deleteTask(ids);
    }


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/deleteSubTask")
    public String deleteSubTask(
            @RequestParam(value = "tId") int tId,
            @RequestParam(value = "stId") int stId
            )
    {
        return TMS.deleteSubTask(tId,stId);
    }


    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/deleteSubSubTask")
    public String deleteSubSubTask(
            @RequestParam(value = "stId") int stId,
            @RequestParam(value = "sstId") int sstId
    ){
        return TMS.deleteSubSubTask(stId,sstId);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/showDueDatesofTask")
    public List<Task_entity> showDueDatesofTask()
    {
        return TMS.showDueDatesofTask();
    }
}