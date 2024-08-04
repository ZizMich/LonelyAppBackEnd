package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByTitle(String Title);
    List<Task> findByTgroup(String TaskGroup);
    List<Task> findByLanguage(String Language);
    @Query(value = "SELECT DISTINCT tgroup FROM tasks WHERE language = :lang", nativeQuery = true)
    List<String> findDistinctGroups(@Param("lang") String lang);

}
