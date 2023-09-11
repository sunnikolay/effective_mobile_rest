package com.nstiazhkin.smapi.controllers.social;

import com.nstiazhkin.smapi.controllers.auth.AccountNotFoundException;
import com.nstiazhkin.smapi.domain.social.Post;
import com.nstiazhkin.smapi.service.social.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController()
@RequestMapping( "/api/social/post" )
@AllArgsConstructor
public class PostController {
    
    private final PostService service;
    
    @PostMapping()
    public ResponseEntity<?> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam( value = "file", required = false ) MultipartFile multipartFile,
            @RequestParam( value = "title" ) String title,
            @RequestParam( value = "message" ) String message
    ) throws Exception {
        return new ResponseEntity<>( service.createPost( title, message, multipartFile, userDetails ), HttpStatus.OK );
    }
    
    @DeleteMapping( "/{postId}" )
    public ResponseEntity<?> remove(
            @PathVariable @NotNull @Min( 1 ) Integer postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws Exception {
        return new ResponseEntity<>( service.removePost( postId, userDetails ), HttpStatus.NO_CONTENT );
    }
    
    @GetMapping()
    public ResponseEntity<?> getAllPosts(
            @RequestParam( required = false, defaultValue = "1", value = "page" ) Integer page,
            @RequestParam( required = false, defaultValue = "10", value = "size" ) Integer size
    ) {
        return new ResponseEntity<>( service.getPosts( PageRequest.of( page - 1, size ) ), HttpStatus.OK );
    }
    
    @GetMapping( "/{username}" )
    public ResponseEntity<?> getAllPostsByUsername(
            @PathVariable @NotNull String username,
            @RequestParam( required = false, defaultValue = "0", value = "page" ) Integer page,
            @RequestParam( required = false, defaultValue = "10", value = "size" ) Integer size
    ) throws AccountNotFoundException {
        return new ResponseEntity<>( service.getPosts( username, PageRequest.of( page, size ) ), HttpStatus.OK );
    }
    
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable @NotNull @Min( 1 ) Integer postId,
            @RequestParam( value = "file", required = false ) MultipartFile multipartFile,
            @RequestParam( value = "title" ) String title,
            @RequestParam( value = "message" ) String message
    ) throws Exception {
        return new ResponseEntity<>(
                service.updatePost( postId, title, message, multipartFile, userDetails ),
                HttpStatus.OK
        );
    }
    
}
