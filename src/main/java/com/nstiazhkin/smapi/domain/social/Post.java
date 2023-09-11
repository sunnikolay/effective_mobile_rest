package com.nstiazhkin.smapi.domain.social;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nstiazhkin.smapi.domain.account.Account;
import com.nstiazhkin.smapi.util.serialize.DateTimeSerialize;
import com.nstiazhkin.smapi.util.serialize.PostOwnerSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Table( name = "posts" )
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;
    
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "account_id" )
    @JsonSerialize( using = PostOwnerSerialize.class )
    private Account owner;
    
    @Column( name = "created", columnDefinition = "DATETIME" )
    @JsonSerialize( using = DateTimeSerialize.class )
    private LocalDateTime created;
    
    @Column( name = "updated", columnDefinition = "DATETIME" )
    @JsonSerialize( using = DateTimeSerialize.class )
    private LocalDateTime updated;
    
    @Column( name = "title", columnDefinition = "varchar(255)" )
    @NotEmpty
    @Size( min = 3, max = 255, message = "The title field must be between 3 and 255 characters long" )
    private String title;
    
    @Column( name = "message", columnDefinition = "text" )
    private String message;
    
    @JsonIgnore
    @Column( name = "image_code", columnDefinition = "varchar(255)" )
    private String imgCode;
    
    @JsonIgnore
    @Column( name = "image_name", columnDefinition = "varchar(255)" )
    private String imgName;
    
    @Column( name = "image_path", columnDefinition = "varchar(255)" )
    private String imgPath;
    
    @Column( name = "deleted", columnDefinition = "bit" )
    @JsonIgnore
    private boolean deleted;
    
}
