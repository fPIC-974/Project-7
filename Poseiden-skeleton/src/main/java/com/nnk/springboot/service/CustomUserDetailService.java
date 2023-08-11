package com.nnk.springboot.service;

import com.nnk.springboot.config.CustomUserDetail;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(CustomUserDetailService.class);

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("loadUserByUsername(" + username + ")");

        User user = userRepository.findByUsername(username);

        if (user == null) {
            logger.error("Invalid credentials");
            throw new RuntimeException("Invalid credentials");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(grantedAuthority);

        return new CustomUserDetail(user.getUsername(), user.getPassword(), roles);
    }
}
