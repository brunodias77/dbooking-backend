package com.brunodias.dbooking.domain.services;

import com.brunodias.dbooking.application.communications.requests.authentication.RegisterUserRequest;
import com.brunodias.dbooking.domain.entities.User;

import java.util.List;

public interface IUserService {
    User registerUser(RegisterUserRequest request);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
