package com.nextgen.backend.response;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    private boolean status;

    public AuthenticationResponse(String token, boolean status) {
        this.token = token;
        this.status = status;
    }
}
