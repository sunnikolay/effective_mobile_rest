package com.nstiazhkin.smapi.util;

import com.nstiazhkin.smapi.payload.response.PageableResponse;
import org.springframework.data.domain.Page;

public class PageConverter {
    
    public static PageableResponse convert( Page<?> page ) {
        return PageableResponse.builder()
                .totalItems( page.getTotalElements() )
                .totalPages( page.getTotalPages() )
                .currentPage( page.getNumber() )
                .data( page.getContent() )
                .build();
    }
    
}
