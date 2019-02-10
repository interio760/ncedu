package com.edunetcracker.startreker.controllers;

import com.edunetcracker.startreker.controllers.exception.RequestException;
import com.edunetcracker.startreker.dao.UserDAO;
import com.edunetcracker.startreker.domain.Role;
import com.edunetcracker.startreker.domain.User;
import com.edunetcracker.startreker.dto.UserDTO;
import com.edunetcracker.startreker.forms.SignUpForm;
import com.edunetcracker.startreker.forms.UserForm;
import com.edunetcracker.startreker.security.jwt.JwtProvider;
import com.edunetcracker.startreker.security.jwtResponse.JwtResponse;
import com.edunetcracker.startreker.util.AuthorityUtils;
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

@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;

    private final String ERROR_USER_ALREADY_EXISTS = "-1";

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider,
                          UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/api/auth/signup")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody SignUpForm signUpForm) {
        if(userDAO.findByUsername(signUpForm.getUsername()).isPresent()){
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
        return ResponseEntity.created(null).body(UserDTO.from(user));
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
