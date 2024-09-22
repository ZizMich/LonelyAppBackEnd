package com.aziz.lonelyapp.restapi.v1;


import com.aziz.lonelyapp.model.ChatEntity;
import com.aziz.lonelyapp.model.UploadedPhotoEntity;
import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.ChatRepository;
import com.aziz.lonelyapp.repository.PhotoRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import com.aziz.lonelyapp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/photos")

public class PhotoController {
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRepository chatRepository;

    private void saveFile(MultipartFile file, String target) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            String name = auth.getName();
            UserEntity user = userRepository.findByName(name).get();
            UploadedPhotoEntity entity = new UploadedPhotoEntity();
            entity.setCreatedAt(System.currentTimeMillis());
            entity.setTarget(target);
            entity.setFromUser(user.getId());// Get the original filename

            UploadedPhotoEntity ent = photoRepository.save(entity);
            user.setAvatar(ent.getId());
            userRepository.save(user);
            String fileName = ent.getId() + ".jpg";
            // Ensure the uploads directory exists
            File uploadDir = new File("photos");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // Save the file to the uploads directory
            Path filePath = Paths.get("photos", fileName);
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

    @GetMapping("/avatar/{chat_id}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String chat_id) throws IOException {
        Optional<UserEntity> userEntity = userRepository.findById(chat_id);
        Optional<ChatEntity> chatEntity = chatRepository.findById(chat_id);
        String avatar;

        if (userEntity.isPresent() && userEntity.get().getAvatar() != null) {
            avatar = String.valueOf(userEntity.get().getAvatar());
        } else if (chatEntity.isPresent() && chatEntity.get().getAvatar() != null) {
            avatar = String.valueOf(chatEntity.get().getAvatar());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Construct the image path
        Path imagePath = Paths.get("photos/" + avatar + ".jpg");
        Resource resource = new FileSystemResource(imagePath);

        // Set headers (optional but good practice)
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + avatar + ".jpg");
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


}


