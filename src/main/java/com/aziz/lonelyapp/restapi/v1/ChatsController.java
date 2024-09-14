package com.aziz.lonelyapp.restapi.v1;

import com.aziz.lonelyapp.model.ChatMemberEntity;
import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.ChatMemberRepository;
import com.aziz.lonelyapp.repository.ChatRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/account/chats")
public class ChatsController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatMemberRepository chatMemberRepository;
    @GetMapping()
    public ResponseEntity<?> getActiveGroups() {

        System.out.println("processed");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> ownId = userRepository.findByName(authentication.getName());
        if(ownId.isEmpty()){
            return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
        }
        else {
            List<ChatMemberEntity> members = chatMemberRepository.findAllByMemberid(ownId.get().getId());
            Map<String, String> response = new HashMap<>();
            for (ChatMemberEntity mem: members) {
                response.put(mem.getGroupid() ,chatRepository.findById(mem.getGroupid()).get().getName());
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }
}

