package com.aziz.lonelyapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "direct_messages")
public class MessageEntity {


    @Id
    private Long id;
    @Column(name="from")
    private Long from;
    @Column(name = "to")
    private Long to;
    @Column(name = "message")
    private String message;


}
