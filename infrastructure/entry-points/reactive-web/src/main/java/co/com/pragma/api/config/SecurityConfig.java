package co.com.pragma.api.config;

import co.com.pragma.security.api.JWTAuthenticationFilter;
import co.com.pragma.security.api.config.CommonSecurityConfig;
import co.com.pragma.security.api.config.SecurityFilterChainBuilder;
import co.com.pragma.security.api.config.SecurityRulesProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Import(CommonSecurityConfig.class) // Importa los beans de seguridad comunes
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityRulesProperties securityRulesProperties;
    private final SecurityFilterChainBuilder securityFilterChainBuilder; // Inyectar SecurityFilterChainBuilder

    public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter,
                          SecurityRulesProperties securityRulesProperties,
                          SecurityFilterChainBuilder securityFilterChainBuilder) { // Añadir al constructor
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.securityRulesProperties = securityRulesProperties;
        this.securityFilterChainBuilder = securityFilterChainBuilder;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling(spec -> spec
                        .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler(new HttpStatusServerAccessDeniedHandler(HttpStatus.FORBIDDEN))) // Added for 403
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges ->
                        securityFilterChainBuilder.applyAuthorizationRules(exchanges, securityRulesProperties))
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
