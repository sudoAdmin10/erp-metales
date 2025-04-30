package com.example.erpmetales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.erpmetales.service.UserRoleService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AccessDeniedHandler accessDeniedHandler() {
                return (request, response, accessDeniedException) -> {
                        response.sendRedirect("fragments/access-denied");

                };
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                // Permitir acceso público a ciertas rutas
                                                .requestMatchers("/", "/login**", "/register", "/css/**", "/js/**")
                                                .permitAll()
                                                // Restringir acceso basado en roles
                                                .requestMatchers("/sales/**", "/sales/suppliers", "/sales/customers",
                                                                "/sales/reports")
                                                .hasAnyAuthority("ROLE_SALES", "ROLE_ADMIN")
                                                .requestMatchers("/production/**")
                                                .hasAnyAuthority("ROLE_PRODUCTION", "ROLE_ADMIN")
                                                .requestMatchers("/quality/**")
                                                .hasAnyAuthority("ROLE_QUALITY", "ROLE_ADMIN")
                                                .requestMatchers("/packaging/**")
                                                .hasAnyAuthority("ROLE_PACKAGING", "ROLE_ADMIN")
                                                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                                                // Todas las demás rutas requieren autenticación
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .loginProcessingUrl("/login") // Asegura que coincide con el form
                                                .usernameParameter("email") // Campo del formulario
                                                .passwordParameter("password") // Campo del formulario
                                                .defaultSuccessUrl("/dashboard", true)
                                                .failureUrl("/login?error=true")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout=true")
                                                .permitAll())
                                .csrf(csrf -> csrf.disable()); // Solo para desarrollo

                return http.build();
        }
}