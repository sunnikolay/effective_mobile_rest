package com.nstiazhkin.smapi.service.security;

import com.nstiazhkin.smapi.repo.AccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final AccountRepo repo;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        return repo.findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException( "User Not Found with username: " + username ) );
    }
    
}
