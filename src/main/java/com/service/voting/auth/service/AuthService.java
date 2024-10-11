package com.service.voting.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.service.voting.auth.dto.res.TokenRes;
import com.service.voting.common.exception.CustomAuthException;
import com.service.voting.config.jwt.JwtService;
import com.service.voting.users.dto.Res.UserRes;
import com.service.voting.users.services.UserService;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenRes login(String username, String password) {
        try {
            UserRes userRes = userService.getUserByUsernameOrEmail(username);
            if (!userRes.isActive()) {
                throw new CustomAuthException("Account is not active");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new CustomAuthException("Password not valid");
            }
            String token = jwtService.generateToken(userRes);
            return new TokenRes(token);
        } catch (UsernameNotFoundException e) {
            throw new CustomAuthException("User not found");
        } catch (Exception e) {
            throw new CustomAuthException("Password not valid");
        }
    }

    public void logout(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        jwtService.blacklistToken(token);
    }
}