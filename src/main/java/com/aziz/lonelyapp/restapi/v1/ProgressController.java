package com.aziz.lonelyapp.restapi.v1;

import com.aziz.lonelyapp.model.RefreshToken;
import com.aziz.lonelyapp.model.Task;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
            return new ResponseEntity<>(progressRepository.findByUserid(ownId.get().getId()), HttpStatus.OK);
         }
    }
}
