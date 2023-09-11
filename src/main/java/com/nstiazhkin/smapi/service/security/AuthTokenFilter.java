package com.nstiazhkin.smapi.service.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    
    private final JwtUtils               jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void doFilterInternal( HttpServletRequest req, HttpServletResponse resp, FilterChain chain )
            throws ServletException, IOException {
        try {
            String jwt = parseJwt( req );
            if ( jwt != null && jwtUtils.validateJwtToken( jwt ) ) {
                String username = jwtUtils.getUserNameFromJwtToken( jwt );
                
                UserDetails userDetails = userDetailsService.loadUserByUsername( username );
                
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken( userDetails,
                                null,
                                userDetails.getAuthorities() );
                
                authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails( req ) );
                
                SecurityContextHolder.getContext().setAuthentication( authentication );
            }
        }
        catch ( Exception e ) {
            logger.error( "Cannot set user authentication: {}", e );
        }
        
        chain.doFilter( req, resp );
    }
    
    private String parseJwt( HttpServletRequest request ) {
        return jwtUtils.getJwtFromCookies( request );
    }
    
}
