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

    /**
     * Filter access to application site, based on user privileges
     *  - Users with ADMIN role will have access to user management
     *  - All users have access to other site pages, as long as they are authenticated
     * @param httpSecurity http request with security enabled
     * @return object built
     * @throws Exception
     */
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
                .and().exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/app/error"))
                .formLogin()
                .defaultSuccessUrl("/bidList/list")
                .and().logout().logoutUrl("/app-logout")
                .permitAll();
        return httpSecurity.build();
    }

    /**
     * Encode password
     * @return encrypted password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
