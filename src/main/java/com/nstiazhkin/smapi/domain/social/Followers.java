package com.nstiazhkin.smapi.domain.social;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nstiazhkin.smapi.domain.account.Account;
import com.nstiazhkin.smapi.util.serialize.DateTimeSerialize;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table( name = "followers" )
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Followers {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;
    
    @Column( name = "created" )
    @JsonSerialize( using = DateTimeSerialize.class )
    private LocalDateTime created;
    
    @ManyToOne
    @JoinColumn( name = "account_from" )
    private Account from;
    
    @ManyToOne
    @JoinColumn( name = "account_to" )
    private Account to;
    
}
