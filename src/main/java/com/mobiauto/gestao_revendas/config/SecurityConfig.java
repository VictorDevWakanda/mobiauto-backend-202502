package com.mobiauto.gestao_revendas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //      http
    //          .authorizeHttpRequests((authorizeHttpRequests) ->
    //              authorizeHttpRequests
    //                  .requestMatchers("/**").hasRole("USER")
    //          )
    //          .formLogin(withDefaults());
    //      return http.build();
    //  }

    //  @Bean
    //  public UserDetailsService userDetailsService() {
    //      UserDetails user = User.withDefaultPasswordEncoder()
    //          .username("user")
    //          .password("password")
    //          .roles("USER")
    //          .build();
    //      UserDetails admin = User.withDefaultPasswordEncoder()
    //          .username("admin")
    //          .password("password")
    //          .roles("ADMIN", "USER")
    //          .build();
    //      return new InMemoryUserDetailsManager(user, admin);
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/v1/usuario/**").authenticated() // Protege os endpoints de usuário
                .anyRequest().permitAll() // Permite outras requisições
            )
            .httpBasic(); // Configuração simplificada para autenticação HTTP Basic

        return http.build();
    }
}
