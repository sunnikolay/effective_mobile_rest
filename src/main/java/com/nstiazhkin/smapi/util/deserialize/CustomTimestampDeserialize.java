package com.nstiazhkin.smapi.util.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTimestampDeserialize extends StdDeserializer<LocalDateTime> {
    
    public CustomTimestampDeserialize() {
        this( null );
    }
    
    public CustomTimestampDeserialize( Class<?> vc ) {
        super( vc );
    }
    
    @Override
    public LocalDateTime deserialize( JsonParser jsonparser, DeserializationContext context ) throws IOException {
        return LocalDateTime.parse( jsonparser.getText(), DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" ) );
    }
    
}
