package com.example.demo.service;

import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.PlayerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;
    private final AdminRepository adminRepository;

    public MyUserDetailsService(PlayerRepository playerRepository, AdminRepository adminRepository) {
        this.playerRepository = playerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return adminRepository.findByUserName(username)
                .map(a -> User
                        .withUsername(a.getUserName())
                        .password(a.getPassword())
                        .authorities("ROLE_ADMIN")
                        .build())
                .orElseGet(() -> playerRepository.findByUserName(username)
                        .map(r -> User
                                .withUsername(r.getUserName())
                                .password(r.getPassword())
                                .authorities("ROLE_USER")
                                .build())
                        .orElseThrow(() -> new UsernameNotFoundException("user nit found" + username))
                );
    }

}


