package com.nstiazhkin.smapi.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableResponse {
    
    @JsonProperty( "total-items" )
    private long totalItems;
    
    @JsonProperty( "total-pages" )
    private int totalPages;
    
    @JsonProperty( "current-page" )
    private int currentPage;
    
    @JsonProperty( "data" )
    private List<?> data;
    
}
