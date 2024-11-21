package com.aziz.lonelyapp.restapi.v1;
import com.aziz.lonelyapp.model.DeviceTokenEntity;
import com.aziz.lonelyapp.model.RefreshToken;
import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.DeviceTokenRepository;
import com.aziz.lonelyapp.repository.TokenRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import com.aziz.lonelyapp.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for managing tokens and device tokens.
 */
@RestController
@RequestMapping("api/v1/tokens")
public class TokenController {

    @Autowired
    private DeviceTokenRepository deviceTokenRepository;

    @Autowired
    private JWTGenerator jwt;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param request A map containing the access token and refresh token.
     * @return A ResponseEntity with a new access token if the refresh token is valid,
     *         or an error message if the refresh token is invalid.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        System.out.println("cought");
        String acessToken = request.get("accessToken");
        String refreshToken = request.get("refreshToken");
        if (acessToken == null || !jwt.validateExpiredToken(acessToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
        }

        Optional<RefreshToken> rtoken = tokenRepository.findByToken(refreshToken);
        if (rtoken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
        String uid = rtoken.get().getId();
        String username = userRepository.findById(uid).get().getEmail();
        String newAccessToken = jwt.generateToken(username);

        return ResponseEntity.ok(new HashMap<String, String>() {
            {
                put("accessToken", newAccessToken);
            }
        });
    }

    /**
     * Sets the device token for the authenticated user.
     *
     * @param request A map containing the device token.
     * @return A ResponseEntity with a success message if the device token is successfully uploaded,
     *         or an error message if the device token is already uploaded.
     */
    @PostMapping("/device")
    public ResponseEntity<?> setDeviceToken(@RequestBody Map<String, String> request) {
        SecurityContext context = SecurityContextHolder.getContext();
        Optional<UserEntity> user =  userRepository.findByName(context.getAuthentication().getName());
        if(user.isPresent()) {
            String deviceToken = request.get("deviceToken");
            System.out.println(deviceToken);
            if(!deviceTokenRepository.existsByDeviceToken(deviceToken)){
                DeviceTokenEntity entity = new DeviceTokenEntity();
                entity.setUserId(user.get().getId());
                entity.setDeviceToken(deviceToken);
                deviceTokenRepository.save(entity);
                return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Device token is already uploaded", HttpStatus.OK);
            }
        }
        else{
            return new ResponseEntity<>("No user found.", HttpStatus.NOT_FOUND);
        }
    }
}
