package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.MessageEntity;
import com.aziz.lonelyapp.model.TaskProgressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectMessagesRepository extends JpaRepository<MessageEntity, Long> {
    @Query(value = "SELECT * FROM direct_messages WHERE to = :id", nativeQuery = true)
    List<MessageEntity> findByRecieverId(@Param("id") Long id);

}
