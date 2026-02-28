package tech.sjostrom.entsoepriceanalyzer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/analysis/**", "/actuator/**").permitAll() // Use requestMatchers for URL patterns
                        .anyRequest().authenticated()
                );
        return http.build(); // Build the HttpSecurity configuration
    }

}
