package com.nstiazhkin.smapi.util.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampSerialize extends StdSerializer<LocalDateTime> {
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" );
    
    public TimestampSerialize() {
        this( null );
    }
    
    public TimestampSerialize( Class<LocalDateTime> t ) {
        super( t );
    }
    
    @Override
    public void serialize( LocalDateTime value, JsonGenerator gen, SerializerProvider arg2 ) throws IOException {
        gen.writeString( formatter.format( value ) );
    }
    
}
