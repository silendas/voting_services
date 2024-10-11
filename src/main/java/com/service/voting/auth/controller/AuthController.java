package com.service.voting.auth.controller;

import com.service.voting.auth.dto.res.TokenRes;

import com.service.voting.auth.dto.req.LoginReq;

import com.service.voting.auth.service.AuthService;

import com.service.voting.common.path.BasePath;

import com.service.voting.common.response.Response;
import com.service.voting.common.response.dto.GlobalDto;
import com.service.voting.common.response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BasePath.BASE_API)
public class AuthController {

    @Autowired
    private AuthService authService;

    private ResponseEntity<Object> buildResponse(Object data, Message messageEnum, int action) {
        GlobalDto dto = new GlobalDto();
        dto.setStatus(messageEnum.getStatusCode());
        dto.setData(data);
        dto.setMessage(messageEnum.getMessage());
        return Response.buildResponse(dto, action);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReq loginReq) {
        TokenRes tokenRes = authService.login(loginReq.getUsername(), loginReq.getPassword());
        return buildResponse(tokenRes, Message.SUCCESSFULLY_LOGIN, 1);
    }


    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return buildResponse(null, Message.SUCCESSFULLY_LOGOUT, 0);
    }
}