package com.Task_1.TaskManagement.Repository;

import com.Task_1.TaskManagement.Entity.Task_entity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface Task_repository extends JpaRepository<Task_entity,Integer>
{
    //display sub task by main task id
    String subTaskQuery="select st.sub_task_id,st.task_id,st.sub_task_name,st.sub_task_deadline,st.sub_task_status \n" +
            "from task t,sub_task st where t.task_id=st.task_id and st.task_id=(?1);";

    @Query(nativeQuery = true,value = subTaskQuery)
    List<String> showSubTask(int taskId);

    //display sub sub task by main task and sub task id
    String subSubTaskQuery="select sst.sub_sub_task_id,sst.sub_task_id,sst.sub_sub_task_name,sst.sub_sub_task_deadline ,sst.sub_sub_task_status\n" +
            "from task t,sub_task st,sub_sub_task sst \n" +
            "where t.task_id=st.task_id and st.sub_task_id=sst.sub_task_id and st.task_id=(?1) and sst.sub_task_id=(?2);";

    @Query(nativeQuery = true,value = subSubTaskQuery)
    List<String> showSubSubTask(int id1,int id2);

    String editSubTaskQuery="update task t,sub_task st \n" +
            "set st.sub_task_name=?3,\n" +
            "st.sub_task_deadline=?4,\n" +
            "st.sub_task_status=?5 \n" +
            "where t.task_id=st.task_id and st.task_id=?2 and st.sub_task_id=?1";
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = editSubTaskQuery)
    void editSubTask(int stId, int tId, String stName, Date stDeadline, String stStatus);

    String editSubSubTaskQuery="update task t,sub_task st,sub_sub_task sst \n" +
            "set sst.sub_sub_task_name=?4,\n" +
            "sst.sub_sub_task_deadline=?5,\n" +
            "sst.sub_sub_task_status=?6\n" +
            "where t.task_id=st.task_id \n" +
            "and st.sub_task_id=sst.sub_task_id \n" +
            "and st.task_id=?1 and st.sub_task_id=?3 and sst.sub_sub_task_id=?2";

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = editSubSubTaskQuery)
    void editSubSubTask(int tId,int sstId, int stId, String sstName, Date sstDeadline, String sstStatus);


    String deleteSubTask="DELETE FROM sub_task \n" +
            "WHERE sub_task_id = ?2 AND task_id = ?1";
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = deleteSubTask)
    void deleteTask(int tId, int stId);


    String deleteSubSubTask="DELETE FROM sub_sub_task \n" +
            "WHERE sub_sub_task_id = ?2 AND sub_task_id = ?1";
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = deleteSubSubTask)
    void deleteSubSubTask(int stId, int sstId);
}
