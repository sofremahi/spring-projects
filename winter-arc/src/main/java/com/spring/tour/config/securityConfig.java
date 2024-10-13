package com.spring.tour.config;

import com.spring.tour.service.impl.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class securityConfig {
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final String[] publicUrl = {"/", "/global-search/**", "/register", "/register/**", "/webjars/**", "/resources/**", "/assets/**", "/css/**", "/summernote/**", "/js/**", "/*.css", "/*.js", "/*.js.map", "/fonts**", "/favicon.ico", "/resources/**", "/error", "/homepage"};

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(publicUrl).permitAll();
            auth.anyRequest().authenticated();
        });
        http.formLogin(form -> form.loginPage("/login").permitAll()
                        .successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/homepage");
                }).cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
