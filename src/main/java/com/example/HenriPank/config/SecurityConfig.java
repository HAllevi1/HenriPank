package com.example.HenriPank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain to manage HTTP requests.
     * Sets up access rules, disables CSRF for JWT compatibility, and
     * enforces a stateless session policy.
     *
     * @param http The HttpSecurity object to be configured.
     * @return The built SecurityFilterChain.
     * @throws Exception If an error occurs during the configuration.
     */
    @Bean //We run this once, holding this object in a box and recalling if we need anywhere in a code
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //Dissalowing CSRF, since were using JWT
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/add", "/api/login").permitAll() //Can access without logging in
                        .anyRequest().authenticated() //Everything else locked.
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Server wont memorize sessions
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides a BCrypt-based password encoder for secure credential hashing.
     * Used by the service layer to protect user passwords before storage.
     *
     * @return A PasswordEncoder implementation using BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //Secure Hashing
    }

}
