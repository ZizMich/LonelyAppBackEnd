package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.MessageEntity;
import com.aziz.lonelyapp.model.TaskProgressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectMessagesRepository extends JpaRepository<MessageEntity, Long> {
    @Query(value = "SELECT * FROM direct_messages WHERE receiver = :id AND id < :start ORDER BY sentdate DESC LIMIT :lim ", nativeQuery = true)
    List<MessageEntity> findByRecieverId(@Param("id") String id, @Param("start") long start, @Param("lim") int lim );

    @Query(value = "SELECT * FROM direct_messages WHERE (sender = :personA AND receiver = :personB) OR (sender = :personB AND receiver = :personA)  AND id < :start ORDER BY sentdate DESC LIMIT :lim",nativeQuery = true)
    List<MessageEntity> getDialog(@Param("personA") String personA, @Param("personB") String personB, @Param("start") long start, @Param("lim") int lim );

    @Query(value = "SELECT DISTINCT sender FROM direct_messages WHERE receiver = :receiver", nativeQuery = true)
    List<String> getDialogs(@Param("receiver") String receiver);

}
