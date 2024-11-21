package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.ChatEntity;
import com.aziz.lonelyapp.model.ChatMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for managing {@link ChatMemberEntity} entities.
 * It extends Spring Data JPA's {@link JpaRepository} to provide CRUD operations and custom queries.
 */
public interface ChatMemberRepository extends JpaRepository<ChatMemberEntity, Long> {

    /**
     * Retrieves a list of {@link ChatMemberEntity} instances associated with the given member ID.
     *
     * @param memberid The unique identifier of the member.
     * @return A list of {@link ChatMemberEntity} instances associated with the given member ID.
     */
    List<ChatMemberEntity> findAllByMemberid(String memberid);

    /**
     * Retrieves a list of {@link ChatMemberEntity} instances associated with the given group ID.
     *
     * @param groupid The unique identifier of the group.
     * @return A list of {@link ChatMemberEntity} instances associated with the given group ID.
     */
    List<ChatMemberEntity> findAllByGroupid(String groupid);
}