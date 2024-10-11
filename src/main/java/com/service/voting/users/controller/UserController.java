package com.service.voting.users.controller;

import com.service.voting.common.path.BasePath;
import com.service.voting.common.response.Response;
import com.service.voting.common.response.dto.GlobalDto;
import com.service.voting.common.reuse.PageConvert;
import com.service.voting.users.dto.Req.UserReq;
import com.service.voting.users.dto.Res.UserRes;
import com.service.voting.users.models.User;
import com.service.voting.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BasePath.BASE_USERS)
public class UserController {

    @Autowired
    private UserService userService;

    private ResponseEntity<Object> buildResponse(Object data, int status, int action) {
        GlobalDto dto = new GlobalDto();
        dto.setStatus(status);
        dto.setData(data);
        return Response.buildResponse(dto, action);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "false") boolean pagination,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page) {
        if (pagination) {
            Pageable pageable = PageRequest.of(page, size);
            Page<UserRes> pagedUsers = userService.getPagUsers(search, pageable);
            return buildResponse(PageConvert.convert(pagedUsers), 200, 1);
        } else {
            List<UserRes> users = userService.getUsers(search);
            return buildResponse(users, 200, 1);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserReq userReq) {
        User user = userService.createUser(userReq);
        return buildResponse(user, 200, 1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserReq userReq) {
        User user = userService.updateUser(id, userReq);
        return buildResponse(user, 200, 1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return buildResponse(null, 200, 0);
    }
}