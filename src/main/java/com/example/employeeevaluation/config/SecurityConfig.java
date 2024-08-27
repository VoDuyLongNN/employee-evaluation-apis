package com.example.employeeevaluation.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private AuthenticationProvider authProvider;
  private JwtFilterConfig jwtFilterConfig;

  @Autowired
  public SecurityConfig(AuthenticationProvider authProvider, JwtFilterConfig jwtFilterConfig) {
    this.authProvider = authProvider;
    this.jwtFilterConfig = jwtFilterConfig;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            (auth) -> {
              auth.requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN", "SYSTEM");
              auth.requestMatchers("/api/v1/user/**").hasRole("USER");
              auth.requestMatchers("/api/v1/auth/**").permitAll();
              auth.anyRequest().authenticated();
            })
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(Customizer.withDefaults())
        .authenticationProvider(authProvider)
        .addFilterBefore(jwtFilterConfig, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
