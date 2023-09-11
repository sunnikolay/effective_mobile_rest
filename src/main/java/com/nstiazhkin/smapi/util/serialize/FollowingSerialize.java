package com.nstiazhkin.smapi.util.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nstiazhkin.smapi.domain.social.Followers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FollowingSerialize extends StdSerializer<List<Followers>> {
    
    public FollowingSerialize() {
        this( null );
    }
    
    public FollowingSerialize( Class<List<Followers>> t ) {
        super( t );
    }
    
    @Override
    public void serialize( List<Followers> followers, JsonGenerator gen, SerializerProvider arg2 ) throws IOException {
        if ( !followers.isEmpty() ) {
            gen.writeStartArray();
    
            for ( Followers follower : followers) {
                gen.writeStartObject();
                gen.writeNumberField( "id", follower.getTo().getId() );
                gen.writeStringField( "username", follower.getTo().getUsername() );
                gen.writeStringField( "fullName", follower.getTo().getFullName() );
                gen.writeStringField( "email", follower.getTo().getEmail() );
                gen.writeEndObject();
            }
    
            gen.writeEndArray();
        }
    }
    
}
