//package com.brunodias.dbooking.infrastructure.filters;
//
//import com.brunodias.dbooking.application.services.TokenService;
//import com.brunodias.dbooking.infrastructure.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class SecurityFilter extends OncePerRequestFilter {
//    @Autowired
//    private TokenService _tokenService;
//
//    @Autowired
//    private UserRepository _userRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        var token = recoverToken(request);
//        if (token != null) {
//            var email = _tokenService.extractUsername(token);
//            UserDetails user = _userRepository.findByEmail(email);
//            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private String recoverToken(HttpServletRequest request) {
//        var authHeader = request.getHeader("Authorization");
//        if (authHeader == null)
//            return null;
//        return authHeader.replace("Bearer ", "");
//    }
//}
