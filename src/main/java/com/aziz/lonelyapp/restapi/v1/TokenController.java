package com.aziz.lonelyapp.restapi.v1;

import com.aziz.lonelyapp.model.RefreshToken;
import com.aziz.lonelyapp.repository.TokenRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import com.aziz.lonelyapp.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/tokens")
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator jwt;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String acessToken = request.get("access_token");
        String refreshToken = request.get("refresh_token");
        if (acessToken == null || !jwt.validateExpiredToken(acessToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
        }

        Optional<RefreshToken> rtoken = tokenRepository.findByToken(refreshToken);
        if (rtoken.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
        Long uid = rtoken.get().getId();
        String username = userRepository.findById(uid).get().getEmail();
        String newAccessToken = jwt.generateToken(username);

        return ResponseEntity.ok(new HashMap<String, String>() {{
                put("accessToken", newAccessToken);
            }});
        }
    }
