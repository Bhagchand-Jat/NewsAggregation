package com.news_aggregation_system.controller;

import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.admin.SystemConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/config")
public class AdminConfigController {

    private final SystemConfigService configService;

    public AdminConfigController(SystemConfigService configService) {
        this.configService = configService;
    }

    @PatchMapping("/{id}/threshold")
    public ResponseEntity<ApiResponse<Integer>> updateThreshold(@PathVariable("id") Long id, @RequestParam int newsThreshold) {
        configService.updateThreshold(id, newsThreshold);
        return ResponseEntity.ok(ApiResponse.ok("Threshold updated", newsThreshold));
    }

    @GetMapping("/threshold")
    public ResponseEntity<ApiResponse<Integer>> getThreshold() {
        int threshold = configService.getCurrentReportThreshold();
        return ResponseEntity.ok(ApiResponse.ok("Current threshold", threshold));
    }
}

