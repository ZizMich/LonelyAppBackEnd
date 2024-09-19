package com.aziz.lonelyapp.restapi.v1;


import com.aziz.lonelyapp.model.UploadedPhotoEntity;
import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.PhotoRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import com.aziz.lonelyapp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/v1/photos")

public class PhotoController {
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    UserRepository userRepository;
    private void saveFile(MultipartFile file, String target) throws IOException {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()){
            String name =auth.getName();
            UserEntity user =  userRepository.findByName(name).get();
            UploadedPhotoEntity entity = new UploadedPhotoEntity();
            entity.setCreatedAt(System.currentTimeMillis());
            entity.setTarget(target);
            entity.setFromUser(user.getId());// Get the original filename
            UploadedPhotoEntity ent =  photoRepository.save(entity);
            String fileName = String.valueOf( ent.getId()) + ".jpeg";
            // Ensure the uploads directory exists
            File uploadDir = new File("photos");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save the file to the uploads directory
            Path filePath = Paths.get("photos" , fileName);
            Files.write(filePath, file.getBytes());
        }
    }
    @PostMapping("avatar")
    public ResponseEntity<?> upload(@RequestParam("image") MultipartFile file) {
        try {
            saveFile(file, "AVATAR");
            return new ResponseEntity<>("uploaded", HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

