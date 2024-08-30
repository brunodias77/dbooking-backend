package com.brunodias.dbooking.application.communications.requests.authentication;

import com.brunodias.dbooking.domain.entities.Role;

import java.time.LocalDate;
import java.util.Collection;

public record RegisterUserRequest(
        String firstName,
        String lastName,
        String phone,
        LocalDate birthDate,
        String email,
        String password
) {
}
