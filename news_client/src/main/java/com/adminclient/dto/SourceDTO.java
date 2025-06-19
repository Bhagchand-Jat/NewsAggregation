package com.adminclient.dto;

public record SourceDTO(
        long   sourceId,
        String sourceUrl,
        String sourceApiKey,
        Boolean active     
) {}
