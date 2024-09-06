package com.brunodias.dbooking.domain.entities;

import com.brunodias.dbooking.domain.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class User extends EntityBase {

    @NotBlank(message = "O campo nome não pode ficar em branco")
    private String firstName;

    @NotBlank(message = "O campo sobrenome não pode ficar em branco")
    private String lastName;

    @NotBlank(message = "O campo telefone não pode ficar em branco")
    private String phone;

    @NotNull(message = "O campo data de nascimento não pode ficar em branco")
    private LocalDate birthDate;

    @NotBlank(message = "O campo de email não pode ficar em branco")
    @Column(unique = true)
    @Email(message = "Por favor, digite o e-mail no formato correto!")
    private String email;

    @NotBlank(message = "O campo de senha não pode ficar em branco")
    @Size(min = 5, message = "A senha deve ter pelo menos 5 caracteres")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookedRoom> bookedRooms = new ArrayList<>();

}
