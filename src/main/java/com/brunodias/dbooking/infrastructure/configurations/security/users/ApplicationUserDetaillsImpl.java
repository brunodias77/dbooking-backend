package com.brunodias.dbooking.infrastructure.configurations.security.users;
import com.brunodias.dbooking.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Classe que implementa UserDetails para representar os detalhes do usuário na
 * aplicação.
 * Essa classe fornece as informações necessárias para o Spring Security
 * autenticar e autorizar o usuário.
 *
 * @author @brunodias.dev
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserDetaillsImpl implements UserDetails {
    private UUID id;
    private String email;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public static ApplicationUserDetaillsImpl buildUserDetails(User user) {
        // Converte as roles do usuário em uma coleção de GrantedAuthority
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        // Cria e retorna um novo objeto HotelUserDetails
        return new ApplicationUserDetaillsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
