package com.nstiazhkin.smapi.controllers.auth;

public class UsernameAlreadyExistsException extends Exception {
    
    public UsernameAlreadyExistsException( String s ) {
        super( "This username("+s+") is already taken by someone else" );
    }
    
}
