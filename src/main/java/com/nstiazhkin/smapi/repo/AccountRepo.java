package com.nstiazhkin.smapi.repo;

import com.nstiazhkin.smapi.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    
    Optional<Account> findByUsername( String name );
    
    Boolean existsByUsername( String username );
    
    Boolean existsByEmail( String email );
    
}
