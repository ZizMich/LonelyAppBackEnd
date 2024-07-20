package com.aziz.lonelyapp.service;

import com.aziz.lonelyapp.model.Task;
import com.aziz.lonelyapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService {
    @Autowired
    private TaskRepository taskRepository;
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> getTasksByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    public List<Task> getTasksByTgroup(String group) {
        return taskRepository.findByTgroup(group);
    }



    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
