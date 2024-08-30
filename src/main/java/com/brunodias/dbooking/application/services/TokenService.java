package com.brunodias.dbooking.application.services;

import com.brunodias.dbooking.infrastructure.configurations.security.users.ApplicationUserDetaillsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import java.util.List;


@Service
public class TokenService {

    @Value("${application.security.jwt.secret-key}") // Injeta o valor da propriedade
    private String secretKey;

    @Value("${application.security.jwt.expiration}") // Injeta o valor da propriedade
    private long jwtExpiration;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);


    public String generateJwtTokenForUser(Authentication authentication){
        ApplicationUserDetaillsImpl userPrincipal = (ApplicationUserDetaillsImpl) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())  // Define o sujeito do token como o nome de usuário
                .claim("roles", roles)  // Adiciona a lista de roles como uma reivindicação
                .setIssuedAt(new Date())  // Define a data de emissão do token
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))  // Define a data de expiração do token
                .signWith(key(), SignatureAlgorithm.HS256)  // Assina o token com o segredo e o algoritmo HS256
                .compact();  // Compacta o token em uma string
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
        try {
            // Analisa o token para verificar sua validade
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;  // O token é válido
        } catch (MalformedJwtException e) {
            logger.error("Token Jwt invalido : {} ", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token Expirado : {} ", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Esse token nao e suportado : {} ", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Nenhuma reivindicação encontrada : {} ", e.getMessage());
        }
        return false;  // O token não é válido
    }

//    /**
//     * Extrai o nome de usuário do token JWT.
//     *
//     * @param token O token JWT do qual extrair o nome de usuário.
//     * @return O nome de usuário extraído do token.
//     */
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    /**
//     * Extrai uma reivindicação específica do token JWT usando um resolvedor de
//     * reivindicação.
//     *
//     * @param token          O token JWT do qual extrair a reivindicação.
//     * @param claimsResolver Função que resolve a reivindicação desejada.
//     * @param <T>            O tipo da reivindicação.
//     * @return O valor da reivindicação extraída.
//     */
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    /**
//     * Extrai todas as reivindicações do token JWT.
//     *
//     * @param token O token JWT do qual extrair as reivindicações.
//     * @return As reivindicações extraídas do token.
//     */
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInKey()) // Define a chave de assinatura para verificar o token.
//                .build()
//                .parseClaimsJws(token) // Analisa o token e obtém o corpo das reivindicações.
//                .getBody();
//    }
//
//    /**
//     * Decodifica a chave secreta para obter a chave de assinatura usada para
//     * assinar o token JWT.
//     *
//     * @return A chave de assinatura.
//     */
//    private Key getSignInKey() {
//        // Decodifica a chave secreta de Base64.
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes); // Cria uma chave de assinatura HMAC a partir dos bytes decodificados.
//    }
//
//    /**
//     * Gera um token JWT para um usuário.
//     *
//     * @param userDetails Detalhes do usuário para incluir no token.
//     * @return O token JWT gerado.
//     */
//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    /**
//     * Gera um token JWT com reivindicações adicionais.
//     *
//     * @param extraClaims Reivindicações adicionais a serem incluídas no token.
//     * @param userDetails Detalhes do usuário para incluir no token.
//     * @return O token JWT gerado.
//     */
//    public String generateToken(
//            Map<String, Object> extraClaims,
//            UserDetails userDetails) {
//        return Jwts
//                .builder()
//                .setClaims(extraClaims) // Define as reivindicações adicionais.
//                .setSubject(userDetails.getUsername()) // Define o nome de usuário como o assunto do token.
//                .setIssuedAt(new Date(System.currentTimeMillis())) // Define a data de emissão do token.
//                .setExpiration(genExpirationDate())
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact(); // Gera o token JWT compactado.
//    }
//
//    /**
//     * Verifica se um token JWT é válido para um usuário.
//     *
//     * @param token       O token JWT a ser verificado.
//     * @param userDetails Detalhes do usuário para comparar com o token.
//     * @return true se o token for válido e não expirado, false caso contrário.
//     */
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    /**
//     * Verifica se um token JWT está expirado.
//     *
//     * @param token O token JWT a ser verificado.
//     * @return true se o token estiver expirado, false caso contrário.
//     */
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date()); // Compara a data de expiração com a data atual.
//    }
//
//    /**
//     * Extrai a data de expiração do token JWT.
//     *
//     * @param token O token JWT do qual extrair a data de expiração.
//     * @return A data de expiração extraída do token.
//     */
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    // Método para gerar a data de expiração
//    private Date genExpirationDate() {
//        return Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")));
//    }
}
