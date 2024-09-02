package com.brunodias.dbooking.application.services;

import com.brunodias.dbooking.application.communications.requests.authentication.RegisterUserRequest;
import com.brunodias.dbooking.application.exceptions.UserAlreadyExistsException;
import com.brunodias.dbooking.domain.entities.Role;
import com.brunodias.dbooking.domain.entities.User;
import com.brunodias.dbooking.domain.services.IUserService;
import com.brunodias.dbooking.infrastructure.repositories.RoleRepository;
import com.brunodias.dbooking.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;
    private final RoleRepository _roleRepository;
    private static final String FIXED_ROLE_NAME = "ROLE_USER"; // Role fixo para todos os usuários

    @Override
    public User registerUser(RegisterUserRequest request) {
        if (_userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(request.email() + " Esse e-mail nao esta disponivel !");
        }
        var passwordEncrypted = _passwordEncoder.encode(request.password());
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .birthDate(request.birthDate())
                .roles(new HashSet<>())
                .phone(request.phone())
                .email(request.email())
                .password(passwordEncrypted).build();
        Role userRole = _roleRepository.findByName("ROLE_USER").get();
        userRole.setId(null);
        user.getRoles().add(userRole);
        return _userRepository.save(user);
    }

    @Transactional
    @Override
    public List<User> getUsers() {
        return _userRepository.findAll();
    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public User getUser(String email) {
        return null;
    }
}
