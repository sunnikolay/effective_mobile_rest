package com.nstiazhkin.smapi.util.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeSerialize extends StdSerializer<LocalDateTime> {
    
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
    
    public DateTimeSerialize() {
        this(null);
    }
    
    public DateTimeSerialize( Class<LocalDateTime> t) {
        super(t);
    }
    
    @Override
    public void serialize( LocalDateTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeString( df.format(value));
    }
    
}
