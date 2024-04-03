package com.hka.verteiltesysteme.services;

import com.hka.verteiltesysteme.models.CustomUserPrincipal;
import com.hka.verteiltesysteme.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserPrincipal(user.get());
    }
}
