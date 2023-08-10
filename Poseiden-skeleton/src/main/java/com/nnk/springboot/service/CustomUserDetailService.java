package com.nnk.springboot.service;

import com.nnk.springboot.config.CustomUserDetail;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        /*GrantedAuthority grantedAuthority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        };*/

        if (user == null) {
            //logger.error("Invalid credentials");
            throw new RuntimeException("Invalid credentials");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(grantedAuthority);

        // FIXME
        // If username = test | Test -> invalid authentication
        // If username = toto -> exception user is null
//        System.out.println(roles.get(0).getAuthority());
        return new CustomUserDetail(user.getUsername(), user.getPassword(), roles);
//        return new CustomUserDetail("toto", "$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW", new ArrayList<>());
    }
}
