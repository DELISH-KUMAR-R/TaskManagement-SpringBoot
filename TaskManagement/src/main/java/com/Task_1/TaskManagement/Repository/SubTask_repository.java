package com.Task_1.TaskManagement.Repository;

import com.Task_1.TaskManagement.Entity.SubTask_entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTask_repository extends JpaRepository<SubTask_entity,Integer> {
}
