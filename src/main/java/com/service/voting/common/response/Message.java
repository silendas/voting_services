package com.service.voting.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {

    // Process
    SUCCESSFULLY_DEFAULT(200, "Process Successful"),
    NOT_FOUND_DEFAULT(404, "Data not found"),
    FAILED_DEFAULT(400, "Process Failed"),
    SUCCESS_FAILED_DEFAULT(400, "There are some request errors"),

    // Auth
    SUCCESSFULLY_LOGIN(200, "Login Successful"),
    SUCCESSFULLY_LOGOUT(200, "Logout Successful"),
    FAILED_LOGOUT(404, "Logout Failed"),
    FAILED_LOGIN(404, "Login Failed"),

    // Exception
    EXCEPTION_ACCESS_DENIED(403, "Access not allowed"),
    EXCEPTION_NOT_FOUND(404, "Not found"),
    EXCEPTION_ALREADY_EXIST(302, "Data already exists"),
    EXCEPTION_BAD_REQUEST(400, "Bad request"),
    EXCEPTION_NOT_SUPPORT_REQUEST(400, "Request not readable"),
    EXCEPTION_INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int statusCode;
    private final String message;
}
