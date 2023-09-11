package com.nstiazhkin.smapi.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nstiazhkin.smapi.domain.social.Followers;
import com.nstiazhkin.smapi.domain.social.Post;
import com.nstiazhkin.smapi.util.serialize.FollowersSerialize;
import com.nstiazhkin.smapi.util.serialize.FollowingSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Table( name = "accounts" )
@Entity
@Builder
@JsonPropertyOrder({"username", "fullName", "email", "active", "roles", "following", "followers"})
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @JsonIgnore
    private Integer id;
    
    @Column( name = "username" )
    @JsonProperty( "username" )
    private String username;
    
    @Column( name = "full_name" )
    @JsonProperty( "fullName" )
    private String fullName;
    
    @Column( name = "email" )
    @JsonProperty( "email" )
    private String email;
    
    @Column( name = "password" )
    @JsonIgnore
    private String password;
    
    @Column( name = "active", columnDefinition = "bit" )
    @JsonProperty( "active" )
    private Integer active;
    
    @ElementCollection( targetClass = Role.class, fetch = FetchType.EAGER )
    @CollectionTable( name = "roles", joinColumns = @JoinColumn( name = "account_id" ) )
    @Enumerated( EnumType.STRING )
    @Column( name = "role_name", columnDefinition = "varchar(255)" )
    @JsonProperty( "roles" )
    private Set<Role> roles;
    
    @OneToMany( mappedBy = "owner", fetch = FetchType.LAZY )
    @JsonIgnore
    private List<Post> posts;
    
    @OneToMany( mappedBy = "from", fetch = FetchType.LAZY )
    @JsonSerialize( using = FollowingSerialize.class )
    private List<Followers> following;
    
    @OneToMany( mappedBy = "to", fetch = FetchType.LAZY )
    @JsonSerialize( using = FollowersSerialize.class )
    private List<Followers> followers;
    
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
    
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return ( getActive() == 1 );
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
    
}
