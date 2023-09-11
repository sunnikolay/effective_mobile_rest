package com.nstiazhkin.smapi.repo;

import com.nstiazhkin.smapi.domain.account.Account;
import com.nstiazhkin.smapi.domain.social.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    
    Optional<Post> findPostById( Integer id );
    
    Page<Post> findAllByDeletedFalse( Pageable pageable );
    
    Page<Post> findAllByOwnerAndDeletedFalse( Account owner, Pageable pageable );
    
}
