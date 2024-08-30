package com.brunodias.dbooking.infrastructure.configurations.security;

import com.brunodias.dbooking.infrastructure.configurations.security.jwt.JwtAuthEntryPoint;
import com.brunodias.dbooking.infrastructure.configurations.security.users.ApplicationUserDetailsService;
import com.brunodias.dbooking.infrastructure.filters.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final ApplicationUserDetailsService _applicationUserDetailsService;
    private final JwtAuthEntryPoint _jwtAuthEntryPoint;

    @Bean
    public AuthTokenFilter authenticationTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(_applicationUserDetailsService); // Define o serviço de detalhes do usuário
        authProvider.setPasswordEncoder(passwordEncoder()); // Define o codificador de senha
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Desabilita proteção CSRF, pois usaremos autenticação JWT
                .exceptionHandling(
                        exception -> exception.authenticationEntryPoint(_jwtAuthEntryPoint)) // Define o EntryPoint para lidar com erros de autenticação
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a política de criação de sessões como stateless (sem estado)
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

        http.authenticationProvider(authenticationProvider()); // Define o provedor de autenticação a ser utilizado
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro de autenticação JWT antes do filtro de autenticação padrão

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable) // Desabilita a proteção CSRF
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll() // Permite acesso não autenticado a todas as rotas
//                )
//                .addFilterBefore(_securityFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
}
