package com.nstiazhkin.smapi.service.security;

import com.nstiazhkin.smapi.config.settings.JwtSettings;
import com.nstiazhkin.smapi.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@AllArgsConstructor
public class JwtUtils {
    
    private final JwtSettings settings;
    
    public String getJwtFromCookies( HttpServletRequest request ) {
        Cookie cookie = WebUtils.getCookie( request, settings.getCookie() );
        if ( cookie != null ) {
            return cookie.getValue();
        }
        else {
            return null;
        }
    }
    
    public ResponseCookie generateJwtCookie( Account account ) {
        return ResponseCookie
                .from( settings.getCookie(), generateTokenFromUsername( account.getUsername() ) )
                .path( "/api" )
                .maxAge( 24 * 60 * 60 )
                .httpOnly( true )
                .build();
    }
    
    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from( settings.getCookie(), null ).path( "/api" ).build();
    }
    
    public String getUserNameFromJwtToken( String token ) {
        return Jwts.parserBuilder().setSigningKey( key() ).build()
                .parseClaimsJws( token ).getBody().getSubject();
    }
    
    private Key key() {
        return Keys.hmacShaKeyFor( Decoders.BASE64.decode( settings.getSecret() ) );
    }
    
    public boolean validateJwtToken( String authToken ) {
        try {
            Jwts.parserBuilder().setSigningKey( key() ).build().parse( authToken );
            return true;
        }
        catch ( MalformedJwtException e ) {
            log.error( "Invalid JWT token: {}", e.getMessage() );
        }
        catch ( ExpiredJwtException e ) {
            log.error( "JWT token is expired: {}", e.getMessage() );
        }
        catch ( UnsupportedJwtException e ) {
            log.error( "JWT token is unsupported: {}", e.getMessage() );
        }
        catch ( IllegalArgumentException e ) {
            log.error( "JWT claims string is empty: {}", e.getMessage() );
        }
        
        return false;
    }
    
    public String generateTokenFromUsername( String username ) {
        return Jwts.builder()
                .setSubject( username )
                .setIssuedAt( new Date() )
                .setExpiration( new Date( ( new Date() ).getTime() + settings.getExpMs() ) )
                .signWith( key(), SignatureAlgorithm.HS256 )
                .compact();
    }
    
}
