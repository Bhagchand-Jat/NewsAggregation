package com.news_aggregation_system.controller;

import com.news_aggregation_system.model.SystemConfig;
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

    @PutMapping("/threshold")
    public ResponseEntity<ApiResponse<Integer>> updateThreshold(@RequestParam int value) {
        SystemConfig updated = configService.updateThreshold(value);
        return ResponseEntity.ok(ApiResponse.ok("Threshold updated", updated.getReportThreshold()));
    }

    @GetMapping("/threshold")
    public ResponseEntity<ApiResponse<Integer>> getThreshold() {
        int threshold = configService.getCurrentReportThreshold();
        return ResponseEntity.ok(ApiResponse.ok("Current threshold", threshold));
    }
}

