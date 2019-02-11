package com.edunetcracker.startreker.controllers;

import com.edunetcracker.startreker.controllers.exception.RequestException;
import com.edunetcracker.startreker.dao.UserDAO;
import com.edunetcracker.startreker.domain.User;
import com.edunetcracker.startreker.dto.UserDTO;
import com.edunetcracker.startreker.forms.EmailFrom;
import com.edunetcracker.startreker.forms.SignUpForm;
import com.edunetcracker.startreker.forms.UserForm;
import com.edunetcracker.startreker.security.jwt.JwtProvider;
import com.edunetcracker.startreker.security.jwtResponse.JwtResponse;
import com.edunetcracker.startreker.service.EmailService;
import com.edunetcracker.startreker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AuthController {

    private static final int ERROR_USER_ALREADY_EXISTS = -1;
    private static final int ERROR_MAIL_ALREADY_EXISTS = -2;
    private static final int ERROR_NO_SUCH_USER = -3;

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserDAO userDAO;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider,
                          UserDAO userDAO,
                          UserService userService,
                          EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userDAO = userDAO;
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping(path = "/api/auth/sign-up")
    public UserDTO signUp(@Valid @RequestBody SignUpForm signUpForm, HttpServletRequest request){
        if (userService.ifUsernameExist(signUpForm.getUsername())) {
            throw new RequestException("Username already exist", ERROR_USER_ALREADY_EXISTS);
        }

        if (userService.ifEmailExist(signUpForm.getEmail())) {
            throw new RequestException("Email already exist", ERROR_MAIL_ALREADY_EXISTS);
        }

        User user = userService.createUser(signUpForm, false);

        emailService.sendRegistrationMessage(signUpForm.getEmail(),
                getContextPath(request),
                jwtProvider.generateToken(user.getUsername()));

        return UserDTO.from(user);
    }

    @PostMapping(path = "/api/auth/password-recovery")
    public EmailFrom passwordRecovery(@Valid @RequestBody EmailFrom emailFrom){
        User user = userService.getUserByEmail(emailFrom.getEmail());

        if (user == null) {
            throw new RequestException("No such user", ERROR_NO_SUCH_USER);
        }

        String newUserPassword = userService.changePasswordForUser(user);

        emailService.sendPasswordRecoveryMessage(emailFrom.getEmail(),
                user.getUsername(),
                newUserPassword);

        return emailFrom;
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

    private String getContextPath(HttpServletRequest request) {
        return request.getRequestURL().toString().replace(request.getRequestURI(), "");
    }
}
