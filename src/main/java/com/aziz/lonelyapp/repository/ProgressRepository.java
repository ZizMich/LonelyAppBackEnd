package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.Role;
import com.aziz.lonelyapp.model.TaskProgressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgressRepository extends JpaRepository<TaskProgressModel, Long> {
    @Query(value = "SELECT DISTINCT tgroup FROM tasks_progress WHERE userid = :userid", nativeQuery = true)
    List<String> findDistinctGroups(@Param("userid") String userid);

    List<TaskProgressModel> findByUserid(String userid);
}
