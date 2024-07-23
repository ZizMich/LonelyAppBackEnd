package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByTitle(String Title);
    List<Task> findByTgroup(String TaskGroup);
    @Query(value = "SELECT DISTINCT tgroup FROM tasks", nativeQuery = true)
    List<String> findDistinctGroups();

}
