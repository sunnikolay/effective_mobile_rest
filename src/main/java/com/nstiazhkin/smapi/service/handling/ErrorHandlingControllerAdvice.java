package com.nstiazhkin.smapi.service.handling;

import com.nstiazhkin.smapi.controllers.auth.AccountNotFoundException;
import com.nstiazhkin.smapi.controllers.auth.EmailAlreadyExistsException;
import com.nstiazhkin.smapi.controllers.auth.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice( annotations = RestController.class )
public class ErrorHandlingControllerAdvice {
    
    @ResponseBody
    @ExceptionHandler( ConstraintViolationException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ResponseEntity<?> onConstraintValidationException( ConstraintViolationException e ) {
        String message = e.getConstraintViolations().stream()
                .map( violation -> violation.getPropertyPath().toString() + ": " + violation.getMessage() )
                .collect( Collectors.joining( ";" ) );
        
        CustomException ce = CustomException.builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.BAD_REQUEST.value() )
                .error( HttpStatus.BAD_REQUEST )
                .message( message )
                .build();
        
        return new ResponseEntity<>( ce, HttpStatus.BAD_REQUEST );
    }
    
    @ResponseBody
    @ExceptionHandler( { AccountNotFoundException.class, UsernameAlreadyExistsException.class,
            EmailAlreadyExistsException.class } )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ResponseEntity<?> onAccountNotFoundException( Exception e, HttpServletRequest request ) {
        CustomException ce = CustomException.builder()
                .timestamp( LocalDateTime.now() )
                .path( request.getRequestURL().toString() )
                .status( HttpStatus.BAD_REQUEST.value() )
                .error( HttpStatus.BAD_REQUEST )
                .message( e.getMessage() )
                .build();
        
        return new ResponseEntity<>( ce, HttpStatus.BAD_REQUEST );
    }
    
    @ResponseBody
    @ExceptionHandler( MissingServletRequestParameterException.class )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ResponseEntity<?> onMissingServletRequestParameterException( MissingServletRequestParameterException e ) {
        CustomException ce = CustomException.builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.BAD_REQUEST.value() )
                .error( HttpStatus.BAD_REQUEST )
                .message( e.getMessage() )
                .build();
        
        return new ResponseEntity<>( ce, HttpStatus.BAD_REQUEST );
    }
    
    @ResponseBody
    @ExceptionHandler( AccessDeniedException.class )
    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    public ResponseEntity<?> onException( Exception e ) {
        CustomException ce = CustomException.builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.UNAUTHORIZED.value() )
                .error( HttpStatus.UNAUTHORIZED )
                .message( e.getMessage() )
                .build();
        
        return new ResponseEntity<>( ce, HttpStatus.UNAUTHORIZED );
    }
    
    @ResponseBody
    @ExceptionHandler( { HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class } )
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ResponseEntity<?> onHttpMessageNotReadableException( Exception e ) {
        CustomException ce = CustomException.builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.BAD_REQUEST.value() )
                .error( HttpStatus.BAD_REQUEST )
                .message( e.getMessage() )
                .build();
        
        return new ResponseEntity<>( ce, HttpStatus.BAD_REQUEST );
    }
    
    @ResponseBody
    @ExceptionHandler( { UnsupportedEncodingException.class, IOException.class } )
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public ResponseEntity<?> onServerException( Exception e ) {
        CustomException ce = CustomException.builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.INTERNAL_SERVER_ERROR.value() )
                .error( HttpStatus.INTERNAL_SERVER_ERROR )
                .message( e.getMessage() )
                .build();
        
        return new ResponseEntity<>( ce, HttpStatus.INTERNAL_SERVER_ERROR );
    }
    
}
