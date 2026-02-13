package com.bernardo_duarte.spring_api.controller;

import com.bernardo_duarte.spring_api.dto.SummaryRequest;
import com.bernardo_duarte.spring_api.dto.SummaryResponse;
import com.bernardo_duarte.spring_api.service.SummaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summarize")
public class SummaryController {
    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping
    public SummaryResponse handleSummarize(@RequestBody SummaryRequest request) {
        String summary = summaryService.summarize(request.text());
        return new SummaryResponse(true, summary);
    }
}
