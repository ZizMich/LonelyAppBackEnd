package com.aziz.lonelyapp.restapi.v1;


import com.aziz.lonelyapp.model.TaskProgressModel;
import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.ProgressRepository;
import com.aziz.lonelyapp.repository.TokenRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("api/v1/progress")
public class ProgressController {
    @Autowired
    ProgressRepository progressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    @GetMapping()
    public ResponseEntity<?> getActiveGroups() {
        System.out.println("processed");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         Optional<UserEntity> ownId = userRepository.findByName(authentication.getName());
         if(ownId.isEmpty()){
             return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
         }
         else {
             List<TaskProgressModel> tasks = progressRepository.findByUserid(ownId.get().getId());
             Map<String, Map<Long, Map<String,Object>>> responsemap = new HashMap<>();
             for(TaskProgressModel task: tasks){
                 System.out.println(task);
                 Map<Long, Map<String,Object>> groupmap= new HashMap<>();
                 Map<String,Object> taskmap = new HashMap<>();
                 groupmap.put(task.getTaskid(),taskmap);
                 taskmap.put("progress", task.getProgress());
                 if(responsemap.containsKey(task.getTgroup())) {
                     if (!responsemap.get(task.getTgroup()).containsKey(task.getTaskid())) {
                         responsemap.get(task.getTgroup()).put(task.getTaskid(), taskmap);
                     }
                 } else{
                     responsemap.put(task.getTgroup(),groupmap);
                 }
             }
            return new ResponseEntity<>(responsemap, HttpStatus.OK);
         }
    }
}
