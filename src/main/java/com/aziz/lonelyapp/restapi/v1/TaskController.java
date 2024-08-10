package com.aziz.lonelyapp.restapi.v1;

import com.aziz.lonelyapp.model.Task;
import com.aziz.lonelyapp.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller for handling REST requests related to tasks.
 */
@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {

    /**
     * Autowired instance of TasksService for interacting with the task repository.
     */
    @Autowired
    private TasksService taskService;

    /**
     * Retrieves all tasks from the repository.
     *
     * @return a list of all tasks
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Retrieves a task by its ID from the repository.
     *
     * @param id the ID of the task to retrieve
     * @return the task with the given ID
     */
    @GetMapping("/groups/{lang}")
    public ResponseEntity<?> getGroups(@PathVariable String lang) {


        List<Task> allTasks = taskService.getGroups(lang);
        Map<String, Map<String, Map<String, Object>>> responsemap = new HashMap<>();
        for (Task task : allTasks) {
            System.out.println(task.getNumber());
            Map<String, Object> taskData = new LinkedHashMap<>();
            Map<String, Map<String, Object>> groupData= new LinkedHashMap<>(); {
            };
            taskData.put("id", task.getId());
            taskData.put("number", task.getNumber());
            taskData.put("description", task.getDescription());

            groupData.put(task.getTitle(), taskData);
            // Check if the group already exists in the response map
            if(responsemap.containsKey(task.getGroup())) {
                if (!responsemap.get(task.getGroup()).containsKey(task.getTitle())) {
                    responsemap.get(task.getGroup()).put(task.getTitle(), taskData);
                }
            } else{
                responsemap.put(task.getGroup(), groupData);
            }

            // Add the task to the corresponding group in the response ma
        }

        return new ResponseEntity<>(responsemap, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    /**
     * Retrieves all tasks with the given title from the repository.
     *
     * @param title the title to search for
     * @return a list of tasks with the given title
     */
    @GetMapping("/title/{title}")
    public List<Task> getTasksByTitle(@PathVariable String title) {
        return taskService.getTasksByTitle(title);
    }

    /**
     * Retrieves all tasks with the given group from the repository.
     *
     * @param group the group to search for
     * @return a list of tasks with the given group
     */
    @GetMapping("/group/{group}")
    public List<Task> getTasksByGroup(@PathVariable String group) {
        return taskService.getTasksByTgroup(group);
    }

    /**
     * Creates a new task in the repository.
     *
     * @param task the task to create
     * @return the created task
     */
//    @PostMapping
//    public Task createTask(@RequestBody Task task) {
//        return taskService.saveTask(task);
//    }

    /**
     * Deletes a task from the repository by its ID.
     *
     * @param id the ID of the task to delete
     */
//    @DeleteMapping("/{id}")
//    public void deleteTask(@PathVariable int id) {
//        taskService.deleteTask(id);
//    }
}
