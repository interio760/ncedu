package com.edunetcracker.startreker.security.jwt;

import com.edunetcracker.startreker.service.UserInformationHolderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@PropertySource("classpath:jwt.properties")
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    private UserInformationHolderService userInformationHolderService;

    @Value("${jwt.jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.jwtAuthenticationExpiration}")
    private int jwtAuthenticationExpiration;

    @Value("${jwt.jwtMailRegistrationExpiration}")
    private int jwtMailRegistrationExpiration;

    @Value("${jwt.jwtRefreshExpiration}")
    private int jwtRefreshExpiration;

    public String generateAccessToken(UserDetails userPrincipal) {
        String userInformationHolder = userInformationHolderService.convertAsString(userPrincipal);

        if (userInformationHolder.equals("")) {
            throw new RuntimeException("Something went wrong");
        }

        return generateToken(userInformationHolder, jwtAuthenticationExpiration);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, jwtMailRegistrationExpiration);
    }

    public String generateMailRegistrationToken(String username) {
        return generateToken(username, jwtMailRegistrationExpiration);
    }

    public String retrieveSubject(String jwt) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody().getSubject();
    }

    public boolean validateToken(String jwt) {
        return validateInputToken(jwt, null);
    }

    public boolean validateToken(String jwt, HttpServletRequest request) {
        return validateInputToken(jwt, request);
    }

    private boolean validateInputToken(String jwt, HttpServletRequest request) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e.getMessage());
            if (request != null) request.setAttribute("isExpired", true);
        } catch (Exception e) {
            logger.error("Invalid JWT -> Message: {} " + e.getMessage());
        }

        return false;
    }

    private String generateToken(String subject, int expiration) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
