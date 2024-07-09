package com.Task_1.TaskManagement.Repository;

import com.Task_1.TaskManagement.Entity.SubSubTask_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubSubTask_repository extends JpaRepository<SubSubTask_entity,Integer>
{

}
