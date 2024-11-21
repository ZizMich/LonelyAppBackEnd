package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This interface represents a repository for managing {@link Task} entities.
 * It extends Spring Data JPA's {@link JpaRepository} interface, providing basic CRUD operations.
 * Additionally, it includes custom query methods for finding tasks based on title, task group, and language.
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {

    /**
     * Finds all tasks with the given title.
     *
     * @param Title the title of the tasks to find
     * @return a list of tasks with the specified title
     */
    List<Task> findByTitle(String Title);

    /**
     * Finds all tasks belonging to the given task group.
     *
     * @param TaskGroup the task group of the tasks to find
     * @return a list of tasks belonging to the specified task group
     */
    List<Task> findByTgroup(String TaskGroup);

    /**
     * Finds all tasks written in the specified language, ordered by their number in ascending order.
     *
     * @param lang the language of the tasks to find
     * @return a list of tasks written in the specified language, ordered by their number
     */
    @Query(value = "SELECT * FROM tasks WHERE language = :lang ORDER BY number ASC", nativeQuery = true)
    List<Task> findByLanguage(@Param("lang") String lang);
}
