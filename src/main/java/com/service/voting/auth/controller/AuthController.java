package com.service.voting.auth.controller;

import com.service.voting.auth.dto.res.TokenRes;
import com.service.voting.auth.service.AuthService;
import com.service.voting.common.path.BasePath;
import com.service.voting.common.response.Response;
import com.service.voting.common.response.dto.GlobalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BasePath.BASE_API)
public class AuthController {

    @Autowired
    private AuthService authService;

    private ResponseEntity<Object> buildResponse(Object data, int status, int action) {
        GlobalDto dto = new GlobalDto();
        dto.setStatus(status);
        dto.setData(data);
        return Response.buildResponse(dto, action);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) {
        TokenRes tokenRes = authService.login(username, password);
        return buildResponse(tokenRes, 200, 1);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return buildResponse(null, 200, 0);
    }
}