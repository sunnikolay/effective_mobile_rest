package com.nstiazhkin.smapi.config.settings;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Getter
@Component
@ConfigurationProperties("server.upload")
public class UploadSettings {
    
    private int prefixLength;
    private String path;
    
}
