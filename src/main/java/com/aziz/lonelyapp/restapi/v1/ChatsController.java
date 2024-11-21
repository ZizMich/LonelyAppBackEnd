package com.aziz.lonelyapp.restapi.v1;

import com.aziz.lonelyapp.dto.GetMessagesDTO;
import com.aziz.lonelyapp.model.ChatEntity;
import com.aziz.lonelyapp.model.ChatMemberEntity;
import com.aziz.lonelyapp.model.MessageEntity;
import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.ChatMemberRepository;
import com.aziz.lonelyapp.repository.ChatRepository;
import com.aziz.lonelyapp.repository.DirectMessagesRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import jakarta.annotation.Nullable;
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
    /**
     * Controller for handing requests for chat operations
     */
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatMemberRepository chatMemberRepository;
    @Autowired
    DirectMessagesRepository messagesRepository;
    /**
     * Retrieves all chats for the authenticated user.
     *
     * @return ResponseEntity containing a Map of chat IDs to chat names if successful,
     *         or an error message with appropriate HTTP status code if the user is not found.
     */
    @GetMapping()
    public ResponseEntity<?> getChats() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> ownId = userRepository.findByName(authentication.getName());
        if(ownId.isEmpty()){
            return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
        }
        else {
            List<ChatMemberEntity> chatmembers = chatMemberRepository.findAllByMemberid(ownId.get().getId());
            List<String> dialogs = messagesRepository.getDialogs(ownId.get().getId());
            Map<String, String> response = new HashMap<>();
            for (ChatMemberEntity mem: chatmembers) {
                response.put(mem.getGroupid() ,chatRepository.findById(mem.getGroupid()).get().getName());
            }
            for (String dialog: dialogs){
                Optional<UserEntity>  user = userRepository.findById(dialog);
                response.put(dialog, user.get().getName());
            }
            return new ResponseEntity<>(response,HttpStatus.OK);
        }


    }
    /**
     * Retrieves messages for a specific chat or user dialog.
     *
     * @param chatId The ID of the chat or user to retrieve messages from.
     * @param start The starting point for message retrieval (e.g., timestamp or message ID).
     * @param limit The maximum number of messages to retrieve.
     * @return ResponseEntity containing a List of MessageEntity objects if successful,
     *         or an error message with appropriate HTTP status code if there's an issue.
     */
    @GetMapping("/messages")
    public ResponseEntity<?> getMessages(@RequestParam( name = "chatId") String chatId,@RequestParam( name = "start") Long start, @RequestParam( name = "limit") int limit  ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> ownId = userRepository.findByName(authentication.getName());
        if(ownId.isEmpty()){
            return new ResponseEntity<>("User is not found", HttpStatus.UNAUTHORIZED);
        }
        else {
            if(chatId.contains("CHAT")){
                boolean flag = false;
                List<ChatMemberEntity> members = chatMemberRepository.findAllByGroupid(chatId);
                for (ChatMemberEntity member:members) {
                    if(Objects.equals(member.getMemberid(), ownId.get().getId())){
                        flag = true;
                    }
                }

                if(flag){
                    List<MessageEntity> messages =  messagesRepository.findByRecieverId(chatId, start, limit);
                    return new ResponseEntity<>(messages, HttpStatus.OK);
                }
                else{
                return new ResponseEntity<>("You are not a member of the group", HttpStatus.FORBIDDEN);
                }
            }
            else if(chatId.contains("USER")){
                Optional<UserEntity> user =  userRepository.findById(chatId);
                if (user.isPresent()) {
                    System.out.println(ownId.get().getId());
                    System.out.println(chatId);
                    List<MessageEntity> messages =  messagesRepository.getDialog(ownId.get().getId(), chatId, start, limit);
                    return new ResponseEntity<>(messages, HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>("Receiver is not found", HttpStatus.NOT_FOUND);
                }


            }
            else{
                return new ResponseEntity<>("Chat id is not correct", HttpStatus.BAD_REQUEST);

            }

        }




    }
    /**
     * Retrieves the members of a specific chat group.
     *
     * @param chatid The ID of the chat group to retrieve members from.
     * @return ResponseEntity containing a List of ChatMemberEntity objects if successful,
     *         or an error message with appropriate HTTP status code if there's an issue.
     */
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

