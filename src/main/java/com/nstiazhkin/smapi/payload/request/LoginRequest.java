package com.nstiazhkin.smapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @NotEmpty
    @Size( min = 3, max = 24, message = "The username field must be between 3 and 24 characters long" )
    private String username;
    
    @NotEmpty()
    @Size( min = 3, max = 24, message = "The password field must be between 3 and 24 characters long" )
    private String password;
    
}
