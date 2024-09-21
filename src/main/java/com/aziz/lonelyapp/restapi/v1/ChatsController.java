package com.aziz.lonelyapp.restapi.v1;

import com.aziz.lonelyapp.model.ChatEntity;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @GetMapping("/members/{chatid}")
    public ResponseEntity<?> getMembers( @PathVariable String chatid) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> ownId = userRepository.findByName(authentication.getName());
        if(ownId.isEmpty()){
            return new ResponseEntity<>("Chat is not found", HttpStatus.NOT_FOUND);
        }
        else {
            boolean flag = false;
            List<ChatMemberEntity> members = chatMemberRepository.findAllByGroupid(chatid);
            for (ChatMemberEntity member:members) {
                if(Objects.equals(member.getMemberid(), ownId.get().getId())){
                    flag = true;
                }
            }
            if(flag){
            return new ResponseEntity<>(members, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("You are not a member of the group", HttpStatus.FORBIDDEN);
            }

        }


    }

}

