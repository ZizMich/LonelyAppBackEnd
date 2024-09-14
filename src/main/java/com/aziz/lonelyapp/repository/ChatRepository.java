package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.ChatEntity;
import com.aziz.lonelyapp.model.TaskProgressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity, String> {
//    ChatEntity findById(Long id);
}
