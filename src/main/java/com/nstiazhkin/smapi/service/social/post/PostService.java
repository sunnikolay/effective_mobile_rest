package com.nstiazhkin.smapi.service.social.post;

import com.nstiazhkin.smapi.config.settings.UploadSettings;
import com.nstiazhkin.smapi.controllers.auth.AccountNotFoundException;
import com.nstiazhkin.smapi.domain.account.Account;
import com.nstiazhkin.smapi.domain.social.Post;
import com.nstiazhkin.smapi.payload.response.PageableResponse;
import com.nstiazhkin.smapi.repo.AccountRepo;
import com.nstiazhkin.smapi.repo.PostRepo;
import com.nstiazhkin.smapi.util.PageConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PostService {
    
    private final AccountRepo    accountRepo;
    private final PostRepo       postRepo;
    private final UploadSettings settings;
    
    public Post createPost( String title, String msg, MultipartFile file, UserDetails details ) throws Exception {
        FileUploadState fileUploadResponse = null;
        if ( file != null ) {
            fileUploadResponse = fileUpload( file );
        }
        
        try {
            Post post = Post.builder()
                    .owner( (Account) details )
                    .title( title )
                    .message( msg )
                    .created( LocalDateTime.now() )
                    .deleted( false )
                    .build();
            
            if ( fileUploadResponse != null ) {
                post.setImgName( fileUploadResponse.getFileName() );
                post.setImgPath( fileUploadResponse.getFilePath() );
                post.setImgCode( fileUploadResponse.getFileCode() );
            }
            
            return postRepo.save( post );
        }
        catch ( Exception e ) {
            throw new Exception( e.getMessage() );
        }
    }
    
    @Transactional
    public String removePost( int postId, UserDetails userDetails ) throws Exception {
        Post post = getPostById( postId );
        
        if ( userDetails.getUsername().equals( post.getOwner().getUsername() ) ) {
            try {
                post.setDeleted( true );
                postRepo.save( post );
                
                return "";
            }
            catch ( Exception e ) {
                throw new Exception( "An error occurred while deleting the post. Message: " + e.getMessage() );
            }
        }
        
        throw new Exception( "You cannot delete this post" );
    }
    
    @Transactional
    public PageableResponse getPosts( PageRequest pageRequest ) {
        Page<Post> posts = postRepo.findAllByDeletedFalse( pageRequest );
        
        return PageConverter.convert( posts );
    }
    
    @Transactional
    public PageableResponse getPosts( String username, PageRequest pageRequest ) throws AccountNotFoundException {
        Account account = accountRepo
                .findByUsername( username )
                .orElseThrow( () -> new AccountNotFoundException( username ) );
        
        Page<Post> posts = postRepo.findAllByOwnerAndDeletedFalse( account, pageRequest );
        
        return PageConverter.convert( posts );
    }
    
    @Transactional
    public Post updatePost( int postId, String title, String msg, MultipartFile file, UserDetails details ) throws Exception {
        Post post = getPostById( postId );
    
        if ( details.getUsername().equals( post.getOwner().getUsername() ) ) {
            post.setTitle( title );
            post.setMessage( msg );
            post.setUpdated( LocalDateTime.now() );
            
            if ( file != null ) {
                Path   uploadPath = Paths.get( post.getImgPath() );
                
                if ( Files.exists( uploadPath ) ) {
                    Files.delete( uploadPath );
                }
                
                FileUploadState response = fileUpload( file );
                post.setImgPath( response.getFilePath() );
                post.setImgName( response.getFileName() );
                post.setImgCode( response.getFileCode() );
            }
            
            return postRepo.save( post );
        }
    
        throw new Exception( "You cannot update this post" );
    }
    
    private Post getPostById( int postId ) throws Exception {
        return postRepo.findPostById( postId ).orElseThrow( () -> new Exception( "Post ID is incorrect" ) );
    }
    
    private FileUploadState fileUpload( MultipartFile multipartFile ) throws IOException {
        String fileName   = StringUtils.cleanPath( multipartFile.getOriginalFilename() );
        long   size       = multipartFile.getSize();
        Path   uploadPath = Paths.get( settings.getPath() );
        
        if ( !Files.exists( uploadPath ) ) {
            Files.createDirectories( uploadPath );
        }
        
        String fileCode = RandomStringUtils.randomAlphanumeric( settings.getPrefixLength() );
        
        Path filePath;
        try ( InputStream inputStream = multipartFile.getInputStream() ) {
            filePath = uploadPath.resolve( fileCode + "-" + fileName );
            Files.copy( inputStream, filePath, StandardCopyOption.REPLACE_EXISTING );
        }
        catch ( IOException ioe ) {
            throw new IOException( "Could not save file: " + fileName, ioe );
        }
        
        return FileUploadState.builder()
                .size( size )
                .fileName( fileName )
                .fileCode( fileCode )
                .filePath( filePath.toString() )
                .build();
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class FileUploadState {
        
        private long   size;
        private String fileName;
        private String fileCode;
        private String filePath;
        
    }
    
}
