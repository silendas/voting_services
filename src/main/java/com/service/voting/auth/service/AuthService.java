package com.service.voting.auth.service;

import java.time.LocalDateTime;

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
import com.service.voting.auth.model.LoginInfo;
import com.service.voting.auth.repository.LoginInfoRepository;
import com.service.voting.common.exception.CustomAuthException;
import com.service.voting.config.jwt.JwtService;
import com.service.voting.users.dto.res.UserRes;
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

    @Autowired
    private LoginInfoRepository loginInfoRepository;

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

            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setDeviceType("Web");
            loginInfo.setLoginDate(LocalDateTime.now());
            loginInfo.setLocation("Unknown"); 
            loginInfo.setUserName(userRes.getUsername());
            loginInfoRepository.save(loginInfo);

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