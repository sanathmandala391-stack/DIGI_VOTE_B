package VotingSystem.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://127.0.0.1:3000"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // 1. Allow ALL preflight OPTIONS requests — browser sends these before every POST/PUT
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 2. Auth endpoints — register & login
                        .requestMatchers("/auth/**").permitAll()

                        // 3. Face login is public — user has no token yet when logging in with face
                        // Face REGISTER needs a token (user must be logged in to register face)
                        .requestMatchers(HttpMethod.POST, "/face/login").permitAll()

                        // 4. Public read — elections and candidates visible without login
                        .requestMatchers(HttpMethod.GET, "/elections").permitAll()
                        .requestMatchers(HttpMethod.GET, "/elections/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/candidates/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/results/**").permitAll()

                        // 5. Admin endpoints — ROLE_ADMIN only
                        // Using hasAuthority (exact string match) not hasRole (adds ROLE_ prefix internally)
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                        // 6. Everything else (vote, user profile, face register) — needs valid JWT
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}