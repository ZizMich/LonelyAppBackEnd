package com.aziz.lonelyapp.restapi.v1;

import com.aziz.lonelyapp.dto.AuthResponseDTO;
import com.aziz.lonelyapp.dto.LoginDto;
import com.aziz.lonelyapp.dto.RegisterDto;
import com.aziz.lonelyapp.model.RefreshToken;
import com.aziz.lonelyapp.model.Role;
import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.RoleRepository;
import com.aziz.lonelyapp.repository.TokenRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import com.aziz.lonelyapp.security.JWTGenerator;
import com.aziz.lonelyapp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final TokenRepository tokenrepository;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, TokenRepository tokenrepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.tokenrepository = tokenrepository;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(loginDto.getEmail());
            String token_str = Util.generateRandomString(30);
            Optional<UserEntity> user = userRepository.findByEmail(loginDto.getEmail());
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setId(user.get().getId());
            refreshToken.setToken(token_str);
            refreshToken.setIssuedat(new Date());
            Calendar calendar = Calendar.getInstance();

            // Add one year to the current date
            calendar.add(Calendar.YEAR, 1);

            // Get the date object for the next year
            Date nextYearDate = calendar.getTime();
            refreshToken.setExpiredate(nextYearDate);
            tokenrepository.save(refreshToken);
            return new ResponseEntity<>(new AuthResponseDTO(token,token_str, user.get().getId()), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            if (!userRepository.existsByEmail(loginDto.getEmail())) {
                return new ResponseEntity<>("This account does not exist check the spelling and try again",
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("The password is incorrect", HttpStatus.UNAUTHORIZED);
            }

        }

    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setName(String.format("USER%s", Math.random()));
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        user.setEmail(registerDto.getEmail());
        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

}