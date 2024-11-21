package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.Role;
import com.aziz.lonelyapp.model.TaskProgressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This interface represents a repository for managing {@link TaskProgressModel} entities.
 * It extends Spring Data JPA's {@link JpaRepository} to provide basic CRUD operations.
 * Additionally, it includes custom query methods for retrieving distinct task groups and
 * finding task progress models by user ID.
 */
public interface ProgressRepository extends JpaRepository<TaskProgressModel, Long> {

    /**
     * Finds distinct task groups for a given user ID.
     *
     * @param userid The unique identifier of the user.
     * @return A list of distinct task groups associated with the given user ID.
     */
    @Query(value = "SELECT DISTINCT tgroup FROM tasks_progress WHERE userid = :userid", nativeQuery = true)
    List<String> findDistinctGroups(@Param("userid") String userid);

    /**
     * Finds all task progress models associated with a given user ID.
     *
     * @param userid The unique identifier of the user.
     * @return A list of task progress models associated with the given user ID.
     */
    List<TaskProgressModel> findByUserid(String userid);
}
