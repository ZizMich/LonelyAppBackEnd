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
    /**
     * Controller for handling requests from api/v1/auth/login or api/v1/auth/register routes.
     */
    private final TokenRepository tokenrepository;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator,
            TokenRepository tokenrepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.tokenrepository = tokenrepository;
    }

    /**
     * Handles user login by authenticating the provided credentials and generating a JWT token.
     *
     * @param loginDto the login data transfer object containing the user's email and password.
     * @return a ResponseEntity containing an AuthResponseDTO with the JWT token, refresh token, and user ID if authentication is successful,
     *         or an error message with the appropriate HTTP status if authentication fails.
     */
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
            return new ResponseEntity<>(new AuthResponseDTO(token, token_str, user.get().getId()), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            if (!userRepository.existsByEmail(loginDto.getEmail())) {
                return new ResponseEntity<>("This account does not exist check the spelling and try again",
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("The password is incorrect", HttpStatus.UNAUTHORIZED);
            }

        }

    }

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerDto the registration data transfer object containing the user's email and password.
     *                    It includes the necessary information to create a new user account.
     * @return a ResponseEntity containing a success message if the registration is successful,
     *         or an error message with the appropriate HTTP status if the registration fails.
     *         The response status will be OK if the user is registered successfully,
     *         BAD_REQUEST if the email is already taken, or NOT_FOUND if the user role is not found.
     */
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setName(String.format("USER%s", Math.random()));
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        user.setEmail(registerDto.getEmail());
        Optional<Role> roles = roleRepository.findByName("USER");
        if (roles.isEmpty()) {
            return new ResponseEntity<>("Role not found!", HttpStatus.NOT_FOUND);
        }
        user.setRoles(Collections.singletonList(roles.get()));

        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

}