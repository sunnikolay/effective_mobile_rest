package com.nstiazhkin.smapi.service.security;

import com.nstiazhkin.smapi.controllers.auth.EmailAlreadyExistsException;
import com.nstiazhkin.smapi.controllers.auth.UsernameAlreadyExistsException;
import com.nstiazhkin.smapi.domain.account.Account;
import com.nstiazhkin.smapi.domain.account.Role;
import com.nstiazhkin.smapi.payload.request.RegisterRequest;
import com.nstiazhkin.smapi.repo.AccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthService {
    
    private final AuthenticationManager authManager;
    private final AccountRepo           repo;
    private final JwtUtils              jwtUtils;
    private final PasswordEncoder       encoder;
    
    public Account getAccount( String username, String password ) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken( username, password )
        );
        SecurityContextHolder.getContext().setAuthentication( auth );
        
        return (Account) auth.getPrincipal();
    }
    
    public ResponseCookie getJwtCookie( Account account ) {
        return jwtUtils.generateJwtCookie( account );
    }
    
    public ResponseCookie getEmptyJwtCookie() {
        return jwtUtils.getCleanJwtCookie();
    }
    
    public Account createAccount( RegisterRequest dto ) throws Exception {
        if ( repo.existsByUsername( dto.getUsername() ) ) {
            throw new UsernameAlreadyExistsException( dto.getUsername() );
        }
        
        if ( repo.existsByEmail( dto.getEmail() ) ) {
            throw new EmailAlreadyExistsException( dto.getEmail() );
        }
        
        Account account = Account.builder()
                .username( dto.getUsername() )
                .fullName( dto.getFullName() )
                .email( dto.getEmail() )
                .password( encoder.encode( dto.getPassword() ) )
                .roles( Collections.singleton( Role.USER ) )
                .active( 1 )
                .build();
        
        try {
            return repo.save( account );
        }
        catch ( Exception e ) {
            throw new Exception( e.getMessage() );
        }
    }
    
}
