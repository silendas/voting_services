package com.service.voting.users.dto.Res;

import com.service.voting.users.models.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRes {
    private String username;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private String address;

    private boolean active;

    private Role role;
}