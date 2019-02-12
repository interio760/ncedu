package com.edunetcracker.startreker.security.jwt;

import com.edunetcracker.startreker.service.UserInformationHolderService;
import com.edunetcracker.startreker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserInformationHolderService userInformationHolderService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = getJWT(httpServletRequest);

        if (jwtProvider.validateToken(jwt)) {
            handleToken(httpServletRequest, jwt);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJWT(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }

    private void handleToken(HttpServletRequest httpServletRequest, String jwt) {
        UserDetails userDetails = userService.
                createUserDetails(userInformationHolderService.
                        convertAsUserInfo(jwtProvider.retrieveSubject(jwt)));

        createAuthentication(httpServletRequest, userDetails);
    }

    private void createAuthentication(HttpServletRequest httpServletRequest, UserDetails userDetails) {
        if (userDetails == null) {
            logger.error("User send invalid token");
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}