package com.brunodias.dbooking.api.controllers;

import com.brunodias.dbooking.domain.entities.User;
import com.brunodias.dbooking.domain.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService _userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(_userService.getUsers(), HttpStatus.FOUND);
    }

}
