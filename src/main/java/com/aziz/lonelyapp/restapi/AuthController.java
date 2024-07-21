package com.aziz.lonelyapp.restapi;

import com.aziz.lonelyapp.dto.AuthResponseDTO;
import com.aziz.lonelyapp.dto.LoginDto;
import com.aziz.lonelyapp.dto.RegisterDto;
import com.aziz.lonelyapp.model.Role;
import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.RoleRepository;
import com.aziz.lonelyapp.repository.UserRepository;
import com.aziz.lonelyapp.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * This controller is used to manage authentication for the application.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * The authentication manager used to authenticate user credentials.
     */
    private AuthenticationManager authenticationManager;

    /**
     * The repository used to store user information.
     */
    private UserRepository userRepository;

    /**
     * The repository used to store role information.
     */
    private RoleRepository roleRepository;

    /**
     * The encoder used to encode user passwords.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * The generator used to generate JWT tokens.
     */
    private JWTGenerator jwtGenerator;

    /**
     * Constructs a new instance of the class and sets the required repositories and
     * encoders.
     *
     * @param authenticationManager The authentication manager used to authenticate
     *                              user credentials.
     * @param userRepository        The repository used to store user information.
     * @param roleRepository        The repository used to store role information.
     * @param passwordEncoder       The encoder used to encode user passwords.
     * @param jwtGenerator          The generator used to generate JWT tokens.
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    /**
     * Authenticates a user with the provided credentials.
     *
     * @param loginDto The DTO containing the user's credentials.
     * @return A response containing the user's JWT token if authentication is successful.
     */
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    /**
     * Registers a new user with the provided information.
     *
     * @param registerDto The DTO containing the user's information.
     * @return A response indicating whether the user was registered successfully.
     */
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByName(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setName(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        user.setEmail(registerDto.getEmail());
        Role roles = roleRepository.findByName("USER").get();
