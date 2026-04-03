package com.farmSphere.infrastructure.config;


import com.farmSphere.infrastructure.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;






@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // admin Public endpoints - no token needed
                        .requestMatchers("/admin/farmers/pending").permitAll()
                        .requestMatchers("/admin/investors/pending").permitAll()
                        .requestMatchers("/admin/farmers/{farmerId}/approve").permitAll()
                        .requestMatchers("/admin/farmers/{farmerId}/reject").permitAll()
                        .requestMatchers("/admin/investors/{investorId}/approve").permitAll()
                        .requestMatchers("/admin/investors/{investorId}/reject").permitAll()
                        // auth Public endpoints - no token needed
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/auth/profile/status").permitAll()
                        .requestMatchers("/auth/reset-password").permitAll()
                        // Protected endpoints - token required
                        .requestMatchers("/auth/login").authenticated()
                        .requestMatchers("/auth/upgrade/farmer").authenticated()
                        .requestMatchers("/auth/upgrade/investor").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        System.out.println("ALLOWED ORIGINS: " + allowedOrigins);

        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        config.setAllowedOrigins(origins);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}