package com.bernardo_duarte.spring_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SummaryRequest(
        @JsonProperty("text") String text
) {}
