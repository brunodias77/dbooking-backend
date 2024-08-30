package com.brunodias.dbooking.application.communications.responses;

import java.util.List;
import java.util.UUID;

public record JwtResponse(
        String email,
        String token,
        String type,
        List<String> roles
) {
    public JwtResponse(String email, String token, List<String> roles) {
        this( email, token, "Bearer", roles);
    }
}
