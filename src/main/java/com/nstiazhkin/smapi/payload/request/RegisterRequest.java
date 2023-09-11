package com.nstiazhkin.smapi.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    
    @Size( min = 3, max = 24, message = "The username field must be between 3 and 24 characters long" )
    @NotEmpty
    @JsonProperty( "username" )
    private @Size( min = 3, max = 20 ) @NotBlank String username;
    
    @Size( max = 100, message = "The fullName field must not exceed 100 characters" )
    @JsonProperty( "fullName" )
    private String fullName;
    
    @Size( min = 9, max = 50 )
    @Email
    @NotEmpty
    @JsonProperty( "email" )
    private String email;
    
    @Size( min = 3, max = 24, message = "The password field must be between 3 and 24 characters long" )
    @NotBlank
    @JsonProperty( "password" )
    private String password;
    
}
