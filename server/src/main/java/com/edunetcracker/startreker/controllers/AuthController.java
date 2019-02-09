package com.edunetcracker.startreker.controllers;

import com.edunetcracker.startreker.dao.UserDAO;
import com.edunetcracker.startreker.domain.User;
import com.edunetcracker.startreker.forms.SignInForm;
import com.edunetcracker.startreker.forms.SignUpForm;
import com.edunetcracker.startreker.security.jwt.JwtProvider;
import com.edunetcracker.startreker.security.jwtResponse.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserDAO userDAO;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider,
                          UserDAO userDAO) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userDAO = userDAO;
    }

    @PostMapping(path = "/api/auth/sign-up", consumes = "application/json")
    public void signUp(@RequestBody SignUpForm signUpForm) {
        User user = new User(signUpForm.getUsername(), signUpForm.getPassword());

        userDAO.save(user, signUpForm.getRoles());
    }

    @GetMapping("/api/auth/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInForm signInForm) {

        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                        signInForm.getUsername(),
                        signInForm.getPassword()));

        String jwt = jwtProvider.generateToken(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,"Bearer", userDetails.getUsername(), userDetails.getAuthorities()));
    }
}
