package com.brunodias.dbooking.infrastructure.configurations.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

/**
 * Configuração de CORS para a aplicação do Lakeside Hotel.
 * Permite que o front-end interaja com a API sem problemas de política de mesma
 * origem.
 *
 * @author Simpson Alfred
 */
@Configuration
@EnableWebMvc
public class CorsConfig {

    // Tempo máximo (em segundos) para o cache do resultado da preflight request
    private static final Long MAX_AGE = 3600L;

    // Define a ordem em que o filtro de CORS será aplicado na cadeia de filtros
    private static final int CORS_FILTER_ORDER = -102;

    /**
     * Configura o filtro de CORS para a aplicação.
     * Define as origens permitidas, cabeçalhos permitidos, métodos HTTP permitidos
     * e outras políticas de CORS.
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        // Configura a origem para onde as requisições CORS são permitidas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permite que as credenciais (cookies, autorizações) sejam enviadas nas
        // requisições
        config.setAllowCredentials(true);

        // Define as origens permitidas para as requisições (neste caso, localhost na
        // porta 5173)
        config.addAllowedOrigin("http://localhost:5174");

        // Define os cabeçalhos que podem ser enviados na requisição
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION, // Cabeçalho para autorização (ex.: token JWT)
                HttpHeaders.CONTENT_TYPE, // Cabeçalho para o tipo de conteúdo (ex.: application/json)
                HttpHeaders.ACCEPT)); // Cabeçalho para os tipos de mídia que o cliente aceita

        // Define os métodos HTTP permitidos
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(), // Permite requisições GET
                HttpMethod.POST.name(), // Permite requisições POST
                HttpMethod.PUT.name(), // Permite requisições PUT
                HttpMethod.DELETE.name())); // Permite requisições DELETE

        // Define o tempo máximo para cache do resultado da preflight request
        config.setMaxAge(MAX_AGE);

        // Registra a configuração CORS para todos os caminhos (/**)
        source.registerCorsConfiguration("/**", config);

        // Cria e configura o filtro de CORS com a configuração definida
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        // Define a ordem do filtro na cadeia de filtros
        bean.setOrder(CORS_FILTER_ORDER);

        // Retorna o bean do filtro de CORS configurado
        return bean;
    }
}
