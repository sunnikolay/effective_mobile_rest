package com.nstiazhkin.smapi.controllers.auth;

import com.nstiazhkin.smapi.domain.account.Account;
import com.nstiazhkin.smapi.domain.account.Role;
import com.nstiazhkin.smapi.payload.request.LoginRequest;
import com.nstiazhkin.smapi.payload.request.RegisterRequest;
import com.nstiazhkin.smapi.repo.AccountRepo;
import com.nstiazhkin.smapi.service.security.AuthService;
import com.nstiazhkin.smapi.service.security.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/api/auth" )
@AllArgsConstructor
public class AuthController {
    
    private final AuthService service;
    
    @PostMapping( "/login" )
    public ResponseEntity<?> login( @Valid @RequestBody LoginRequest loginRequest ) {
        Account account = service.getAccount( loginRequest.getUsername(), loginRequest.getPassword() );
        ResponseCookie jwtCookie = service.getJwtCookie( account );
        
        return ResponseEntity
                .ok()
                .header( HttpHeaders.SET_COOKIE, jwtCookie.toString() )
                .body( account );
    }
    
    @PostMapping( value = "/register", produces = "application/json;charset=utf-8" )
    public ResponseEntity<?> registerUser( @Valid @RequestBody RegisterRequest registerRequest ) throws Exception {
        return new ResponseEntity<>( service.createAccount( registerRequest ), HttpStatus.OK );
    }

    @GetMapping( "/logout" )
    public ResponseEntity<?> logoutAccount() {
        return ResponseEntity
                .ok()
                .header( HttpHeaders.SET_COOKIE, service.getEmptyJwtCookie().toString() )
                .body( "" );
    }
    
}
