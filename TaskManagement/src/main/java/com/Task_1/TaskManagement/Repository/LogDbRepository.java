package com.Task_1.TaskManagement.Repository;

import com.Task_1.TaskManagement.Entity.LogDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogDbRepository extends JpaRepository<LogDb,Integer>
{
    Optional<LogDb> findByEmail(String email);
}
