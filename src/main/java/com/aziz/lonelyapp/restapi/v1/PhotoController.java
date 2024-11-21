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

    /**
     * Saves a uploaded file to the server and creates a corresponding database entry.
     *
     * @param file   The MultipartFile object representing the uploaded file.
     * @param target A string indicating the target use of the file (e.g., "AVATAR" or "CHAT").
     * @return The ID of the saved UploadedPhotoEntity, or null if the user is not authenticated.
     * @throws IOException If there's an error while writing the file to the server.
     */
    private Long saveFile(MultipartFile file, String target) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            String name = auth.getName();
            UserEntity user = userRepository.findByName(name).get();
            UploadedPhotoEntity entity = new UploadedPhotoEntity();
            entity.setCreatedAt(System.currentTimeMillis());
            entity.setTarget(target);
            entity.setFromUser(user.getId());// Get the original filename
            UploadedPhotoEntity ent = photoRepository.save(entity);
            String fileName = ent.getId() + ".jpg";
            // Ensure the uploads directory exists
            File uploadDir = new File("photos");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // Save the file to the uploads directory
            Path filePath = Paths.get("photos", fileName);
            Files.write(filePath, file.getBytes());
            return ent.getId();
        }
        return null;
    }

    /**
     * Handles the upload of a user's avatar image.
     *
     * @param file The MultipartFile object representing the uploaded avatar image.
     * @return A ResponseEntity with a success message if the upload is successful, or an error message if it fails.
     */
    @PostMapping("avatar")
    public ResponseEntity<?> upload(@RequestParam("image") MultipartFile file) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            UserEntity user = userRepository.findByName(name).get();
            Long file_id = saveFile(file, "AVATAR");
            user.setAvatar(file_id);
            userRepository.save(user);
            return new ResponseEntity<>("uploaded", HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the upload of a chat picture.
     *
     * @param file    The MultipartFile object representing the uploaded chat picture.
     * @param chat_id The ID of the chat to which the picture should be associated.
     * @return A ResponseEntity with a success message if the upload is successful, or an error message if it fails.
     */
    @PostMapping("chat/{chat_id}")
    public ResponseEntity<?> chatPicture(@RequestParam("image") MultipartFile file, @PathVariable String chat_id) {
        try {
            Long file_id = saveFile(file, "CHAT");
            Optional<ChatEntity> ent = chatRepository.findById(chat_id);
            if(ent.isPresent()){
                ChatEntity chat = ent.get();
                chat.setAvatar(file_id);
                chatRepository.save(chat);
            }
            else{
                return new ResponseEntity<>("This chat does not exist", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>("uploaded", HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the avatar image for a given user or chat.
     *
     * @param chat_id The ID of the user or chat whose avatar is to be retrieved.
     * @return A ResponseEntity containing the avatar image resource if found, or a NOT_FOUND status if not found.
     * @throws IOException If there's an error while reading the image file.
     */
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


