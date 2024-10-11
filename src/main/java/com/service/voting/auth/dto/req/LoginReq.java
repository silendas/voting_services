package com.service.voting.auth.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {
    @NotBlank(message = "Username harus diisi")
    private String username;
    @NotBlank(message = "Password harus diisi")
    private String password;
}
