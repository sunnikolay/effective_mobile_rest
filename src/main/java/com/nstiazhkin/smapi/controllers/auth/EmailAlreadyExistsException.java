package com.nstiazhkin.smapi.controllers.auth;

public class EmailAlreadyExistsException extends Exception {
    
    public EmailAlreadyExistsException( String s ) {
        super( "This email("+s+") is already taken by someone else" );
    }
    
}
