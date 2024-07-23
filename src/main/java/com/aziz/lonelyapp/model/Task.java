package com.aziz.lonelyapp.model;

import jakarta.persistence.*;

/**
 * Entity class for Task.
 * This class represents a task in the task list.
 * Each task has an ID, a number, a group, a title, and a description.
 * The ID is generated automatically by the database.
 * The number is a unique identifier for each task.
 * The group is a string that represents the group of tasks.
 * The title is a string that represents the title of the task.
 * The description is a string that represents the description of the task.
 */
@Entity
@Table(name = "tasks")
public class Task {
    /**
     * The ID of the task.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The number of the task.
     */
    @Column(name = "number")
    private int number;

    /**
     * The group of the task.
     */
    @Column(name = "tgroup")
    private String tgroup;

    /**
     * The title of the task.
     */
    @Column(name = "title")
    private String title;

    /**
     * The description of the task.
     */
    @Column(name = "description")
    private String description;

    @Column(name="language")
    private String language;

    /**
     * Get the description of the task.
     * 
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the task.
     * 
     * @param description The description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the title of the task.
     * 
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the task.
     * 
     * @param title The title of the task.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the group of the task.
     * 
     * @return The group of the task.
     */
    public String getGroup() {
        return tgroup;
    }

    /**
     * Set the group of the task.
     * 
     * @param tgroup The group of the task.
     */
    public void setGroup(String tgroup) {
        this.tgroup = tgroup;
    }

    /**
     * Get the number of the task.
     * 
     * @return The number of the task.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set the number of the task.
     * 
     * @param number The number of the task.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get the ID of the task.
     * 
     * @return The ID of the task.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the task.
     * 
     * @param id The ID of the task.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
