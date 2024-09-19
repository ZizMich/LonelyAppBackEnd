package com.aziz.lonelyapp.model;

import jakarta.persistence.*;

@Entity
@Table(name ="uploaded_photos" )
public class UploadedPhotoEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="target")
    private String target;
    @Column(name= "createdat")
    private Long createdAt;


    @Column(name = "fromuser")
    private String fromUser;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }





    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}
