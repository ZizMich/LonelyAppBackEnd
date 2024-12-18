package com.aziz.lonelyapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 * This class represents a task progress in a system. It is used to store and retrieve
 * progress information for individual tasks and users.
 *
 * @author Aziz
 * @version 1.0
 *
 */
@Entity
@Table(name = "tasks_progress")
public class TaskProgressModel {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "userid")
    private String userid;
    @Column(name = "tgroup")
    private String tgroup;

    @Column(name = "taskid")
    private Long taskid;

    @Column(name = "progress")
    private int progress;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTgroup() {
        return tgroup;
    }

    public void setTgroup(String tgroup) {
        this.tgroup = tgroup;
    }

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
