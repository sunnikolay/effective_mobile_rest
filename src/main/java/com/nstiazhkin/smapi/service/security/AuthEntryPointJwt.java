package com.nstiazhkin.smapi.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    
    @Override
    public void commence( HttpServletRequest req, HttpServletResponse resp, AuthenticationException e )
            throws IOException, ServletException {
        log.error( "Unauthorized error: {}", e.getMessage() );
        
        resp.setContentType( MediaType.APPLICATION_JSON_VALUE );
        resp.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
        
        final Map<String, Object> body = new HashMap<>();
        body.put( "status", HttpServletResponse.SC_UNAUTHORIZED );
        body.put( "error", "Unauthorized" );
        body.put( "message", e.getMessage() );
        body.put( "path", req.getServletPath() );
        
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue( resp.getOutputStream(), body );
    }
    
}
