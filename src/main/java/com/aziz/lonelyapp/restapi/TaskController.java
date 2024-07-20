package com.aziz.lonelyapp.restapi;

import com.aziz.lonelyapp.model.Task;
import com.aziz.lonelyapp.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TasksService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/title/{title}")
    public List<Task> getTasksByTitle(@PathVariable String title) {
        return taskService.getTasksByTitle(title);
    }

    @GetMapping("/group/{group}")
    public List<Task> getTasksByGroup(@PathVariable String group) {
        return taskService.getTasksByTgroup(group);
    }


    @PostMapping
    public Task createTask(@RequestBody Task task) {
        System.out.println(task.getId());
        return taskService.saveTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }
}