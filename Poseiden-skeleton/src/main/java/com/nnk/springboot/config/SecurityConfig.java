package com.nnk.springboot.config;

import com.mysql.cj.protocol.AuthenticationProvider;
import com.nnk.springboot.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
//                .requestMatchers("/").permitAll()
                .requestMatchers("/user/list").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/user/**").hasAuthority("ADMIN")
                .requestMatchers("/user/add/**").hasAuthority("ADMIN")
                .requestMatchers("/user/update/**").hasAuthority("ADMIN")
                .requestMatchers("/user/validate").hasAuthority("ADMIN")
                .requestMatchers("/user/delete/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .defaultSuccessUrl("/bidList/list");
        return httpSecurity.build();
    }

    @Bean
    public SecurityFilterChain bidFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
            .authorizeHttpRequests()
//                .requestMatchers("/").permitAll()
            .requestMatchers("/bidList/list").hasAuthority("USER")
            .requestMatchers("/bidList/list").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "/user/**").hasAuthority("ADMIN")
            .requestMatchers("/bidList/add/**").hasAuthority("ADMIN")
            .requestMatchers("/bidList/update/**").hasAuthority("ADMIN")
            .requestMatchers("/bidList/validate").hasAuthority("ADMIN")
            .requestMatchers("/bidList/delete/**").hasAuthority("ADMIN")
            /*.requestMatchers("/user/update/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/user/**").hasRole("ADMIN")
            .requestMatchers("/user/validate").hasRole("ADMIN")
            .requestMatchers("/user/delete/**").hasRole("ADMIN")*/
            .anyRequest()
            .authenticated()
            .and().formLogin();
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailService);
        return provider;
    }*/
}
