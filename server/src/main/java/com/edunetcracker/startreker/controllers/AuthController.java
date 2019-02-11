package com.edunetcracker.startreker.controllers;

import com.edunetcracker.startreker.controllers.exception.RequestException;
import com.edunetcracker.startreker.dao.UserDAO;
import com.edunetcracker.startreker.domain.Role;
import com.edunetcracker.startreker.domain.User;
import com.edunetcracker.startreker.dto.UserDTO;
import com.edunetcracker.startreker.forms.ConfirmationForm;
import com.edunetcracker.startreker.forms.SignUpForm;
import com.edunetcracker.startreker.forms.UserForm;
import com.edunetcracker.startreker.security.jwt.JwtProvider;
import com.edunetcracker.startreker.security.jwtResponse.JwtResponse;
import com.edunetcracker.startreker.services.EmailService;
import com.edunetcracker.startreker.util.AuthorityUtils;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

    private final String ERROR_USER_ALREADY_EXISTS = "-1";

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider,
                          UserDAO userDAO, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @PostMapping(path = "/api/auth/sign-up")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody SignUpForm signUpForm) {
        if (userDAO.findByUsername(signUpForm.getUsername()).isPresent()) {
            throw new RequestException(ERROR_USER_ALREADY_EXISTS);
        }
        User user = new User(signUpForm.getUsername(), passwordEncoder.encode(signUpForm.getPassword()));
        List<Role> roleList = new ArrayList<>();
        roleList.add(AuthorityUtils.ROLE_USER);
        if (signUpForm.getIs_carrier()) {
            roleList.add(AuthorityUtils.ROLE_CARRIER);
        }
        user.setUserRoles(roleList);
        userDAO.save(user);

        String jwt = jwtProvider.generateToken(user);

        emailService.sendSimpleMessage(signUpForm.getEmail(), "Confirmation"
                , "Hello, " + user.getUsername() + "! Your token is " + jwt );

        return ResponseEntity.created(null).body(UserDTO.from(user));
    }

    @PostMapping(path = "/api/log-out")
    public String logOut(@Valid @RequestBody SignUpForm signUpForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new RequestException("User is not authenticated!");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<User> userOptional = userDAO.findByUsername(
                jwtProvider.retrieveUsername(userDetails.getUsername()));

        if (!userOptional.isPresent())
            throw new RequestException("No such user!");

        User user = userOptional.get();
        user.setUserRefreshToken(null);
        userDAO.save(user);

        return "OK";
    }

    @PostMapping(path = "api/auth/confirmPassword")
    public ResponseEntity<String> confirmPassword(@Valid @RequestParam("token") String token) {

        if (!jwtProvider.validateToken(token))
            return ResponseEntity.created(null).body("NOT OK1");

        Optional<User> userOptional = userDAO.findByUsername(
                jwtProvider.retrieveUsername(token));

        if (!userOptional.isPresent())
            return ResponseEntity.created(null).body("NOT OK2");

        User user = userOptional.get();
        user.setUserEnabled(true);
        userDAO.save(user);

        return ResponseEntity.created(null).body("OK");
    }


    @PostMapping(path = "/api/auth/adminreg")
    public String signUpAdmin(@Valid @RequestBody SignUpForm signUpForm) {
        return passwordEncoder.encode(signUpForm.getPassword());
    }

    @PostMapping("/api/auth/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserForm signInForm) {
        try {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(
                            signInForm.getUsername(),
                            signInForm.getPassword()));

            String jwt = jwtProvider.generateToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponse(jwt, "Bearer", userDetails.getUsername(), userDetails.getAuthorities()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
