package com.nstiazhkin.smapi.service.handling;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nstiazhkin.smapi.util.deserialize.CustomTimestampDeserialize;
import com.nstiazhkin.smapi.util.serialize.TimestampSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder( { "timestamp", "path", "error", "message", "status" } )
class CustomException {
    
    @JsonProperty( "timestamp" )
    @JsonDeserialize( using = CustomTimestampDeserialize.class )
    @JsonSerialize( using = TimestampSerialize.class )
    private LocalDateTime timestamp;
    
    @JsonProperty( "status" )
    private int status;
    
    @JsonProperty( "error" )
    private HttpStatus error;
    
    @JsonProperty( "message" )
    private String message;
    
    @JsonProperty( "path" )
    private String path;
    
}
