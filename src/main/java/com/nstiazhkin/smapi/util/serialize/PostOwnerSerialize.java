package com.nstiazhkin.smapi.util.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nstiazhkin.smapi.domain.account.Account;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostOwnerSerialize extends StdSerializer<Account> {
    
    public PostOwnerSerialize() {
        this( null );
    }
    
    public PostOwnerSerialize( Class<Account> t ) {
        super( t );
    }
    
    @Override
    public void serialize( Account account, JsonGenerator gen, SerializerProvider arg2 ) throws IOException {
        gen.writeString( account.getUsername() );
    }
    
}
