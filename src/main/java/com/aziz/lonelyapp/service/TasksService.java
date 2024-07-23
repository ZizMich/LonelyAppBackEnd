package com.aziz.lonelyapp.service;

import com.aziz.lonelyapp.model.Task;
import com.aziz.lonelyapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class provides a service for tasks.
 * It can get all tasks, a single task by id, tasks by title, tasks by group,
 * and save a task to the database.
 * It can also delete a task by its id.
 */
@Service
public class TasksService {
    @Autowired
    private TaskRepository taskRepository;

    /**
     * This method returns a list of all tasks in the database.
     * 
     * @return a list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * This method returns a task by its id.
     * 
     * @param id the id of the task to return
     * @return the task with the given id
     */
    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    /**
     * This method returns a list of tasks that match the given title.
     * 
     * @param title the title of the tasks to return
     * @return a list of tasks with the given title
     */
    public List<Task> getTasksByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    /**
     * This method returns a list of tasks that match the given group.
     * 
     * @param group the group of the tasks to return
     * @return a list of tasks with the given group
     */
    public List<Task> getTasksByTgroup(String group) {
        return taskRepository.findByTgroup(group);
    }

    /**
     * This method saves the given task to the database.
     * 
     * @param task the task to save
     * @return the saved task
     */
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<String> getGroups(String lang){
        return taskRepository.findDistinctGroups(lang);
    }
    /**
     * This method deletes the task with the given id from the database.
     * 
     * @param id the id of the task to delete
     */
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
