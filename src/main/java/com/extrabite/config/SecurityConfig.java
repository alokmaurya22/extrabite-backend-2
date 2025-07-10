package com.extrabite.config;

import com.extrabite.repository.BlacklistTokenRepository;
import com.extrabite.util.JwtUtil;
import com.extrabite.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final BlacklistTokenRepository blacklistTokenRepository;
    private final ApiKeyAuthFilter apiKeyAuthFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            BlacklistTokenRepository blacklistTokenRepository,
            ApiKeyAuthFilter apiKeyAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.blacklistTokenRepository = blacklistTokenRepository;
        this.apiKeyAuthFilter = apiKeyAuthFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(
                List.of(new CustomAuthenticationProvider(userDetailsService, passwordEncoder)));
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, userDetailsService, blacklistTokenRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/browse/**",
                                "/api/directory/**",
                                "/api/welcome",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/",
                                "/api/analytics/statistics/**")
                        .permitAll()
                        // Allow public access to reject-by-platform endpoint
                        .requestMatchers("/api/donations/*/reject-by-platform").permitAll()
                        // Allow public access to expire-by-expiry-time endpoint
                        .requestMatchers("/api/donations/*/expire-by-expiry-time").permitAll()
                        // Allow public access to scheduler control endpoints
                        .requestMatchers("/api/scheduler/**").permitAll()
                        // Allow public access to changeTargetedDonationStatus endpoint (corrected path)
                        .requestMatchers("/api/donations/changeTargetedDonationStatus/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("SUPER_ADMIN")
                        .requestMatchers("/api/user/**").authenticated()
                        .requestMatchers("/api/donations/**").authenticated()
                        .requestMatchers("/api/requests/**").authenticated()
                        .requestMatchers("/api/ratings/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
