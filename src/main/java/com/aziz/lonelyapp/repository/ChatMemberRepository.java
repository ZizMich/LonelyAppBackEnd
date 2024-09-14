package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.ChatEntity;
import com.aziz.lonelyapp.model.ChatMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMemberRepository extends JpaRepository<ChatMemberEntity, Long> {
    List<ChatMemberEntity> findAllByMemberid(Long memberid);

}