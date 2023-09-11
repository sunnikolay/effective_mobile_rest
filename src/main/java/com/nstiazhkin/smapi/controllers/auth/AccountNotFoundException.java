package com.nstiazhkin.smapi.controllers.auth;

public class AccountNotFoundException extends Exception {
    
    public AccountNotFoundException( String s ) {
        super( "Account[" + s + "] not found" );
    }
    
}
