package com.brunodias.dbooking.api.controllers;

import com.brunodias.dbooking.application.communications.requests.authentication.LoginUserRequest;
import com.brunodias.dbooking.application.communications.requests.authentication.RegisterUserRequest;
import com.brunodias.dbooking.application.communications.responses.JwtResponse;
import com.brunodias.dbooking.application.exceptions.UserAlreadyExistsException;
import com.brunodias.dbooking.application.services.TokenService;
import com.brunodias.dbooking.domain.services.IUserService;
import com.brunodias.dbooking.infrastructure.configurations.security.users.ApplicationUserDetaillsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager _authenticationManager;
    private final TokenService _tokenService;
    private final IUserService _userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequest request){
        try{
            _userService.registerUser(request);
            return ResponseEntity.ok("Usuario registrado com sucesso !");

        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginUserRequest request){
        Authentication authentication = _authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = _tokenService.generateJwtTokenForUser(authentication);
        ApplicationUserDetaillsImpl userDetails = (ApplicationUserDetaillsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResponse(
                userDetails.getEmail(),
                jwt,
                roles));
    }
}
