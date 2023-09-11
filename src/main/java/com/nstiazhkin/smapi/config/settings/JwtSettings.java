package com.nstiazhkin.smapi.config.settings;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Getter
@Component
@ConfigurationProperties("jwt-settings")
public class JwtSettings {
    
    private String secret;
    private String cookie;
    private Integer expMs;
    
}
