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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Permitir chamadas de localhost:3000
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("*")); // Permitir todos os cabeçalhos
        configuration.setAllowCredentials(true); // Permitir envio de credenciais (cookies, headers de autenticação)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and() 
                .csrf(AbstractHttpConfigurer::disable) 
                .exceptionHandling(
                        exception -> exception.authenticationEntryPoint(_jwtAuthEntryPoint)) // Configurar EntryPoint para lidar com exceções
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Definir a política de sessão como STATELESS
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Somente ADMIN pode acessar /admin/**
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Somente USER ou ADMIN podem acessar /user/**
                        .anyRequest().permitAll()); // Qualquer outra requisição é permitida
        http.authenticationProvider(authenticationProvider()); // Configurar o provedor de autenticação
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Adicionar o filtro de autenticação antes do UsernamePasswordAuthenticationFilter
        return http.build();
    }
}