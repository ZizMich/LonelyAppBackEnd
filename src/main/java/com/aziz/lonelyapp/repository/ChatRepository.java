package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.ChatEntity;
import com.aziz.lonelyapp.model.TaskProgressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * This interface represents a repository for managing {@link ChatEntity} entities.
 * It extends Spring Data JPA's {@link JpaRepository} interface, providing basic CRUD operations
 * and additional methods for custom queries.
 */
public interface ChatRepository extends JpaRepository<ChatEntity, String> {
//    ChatEntity findById(Long id);
}
