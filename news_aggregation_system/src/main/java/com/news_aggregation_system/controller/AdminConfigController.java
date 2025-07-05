package com.news_aggregation_system.controller;

import com.news_aggregation_system.response.ApiResponse;
import com.news_aggregation_system.service.admin.SystemConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.news_aggregation_system.service.common.Constant.*;

@RestController
@RequestMapping("/api/admin/config")
public class AdminConfigController {

    private final SystemConfigService configService;

    public AdminConfigController(SystemConfigService configService) {
        this.configService = configService;
    }

    @PatchMapping("/threshold")
    public ResponseEntity<ApiResponse<Integer>> updateThreshold(@RequestParam(NEW_THRESHOLD) int newsThreshold) {
        configService.updateThreshold(newsThreshold);
        return ResponseEntity.ok(ApiResponse.ok(THRESHOLD_UPDATED_SUCCESSFULLY, newsThreshold));
    }

    @GetMapping("/threshold")
    public ResponseEntity<ApiResponse<Integer>> getThreshold() {
        int threshold = configService.getCurrentReportThreshold();
        return ResponseEntity.ok(ApiResponse.ok(CURRENT_THRESHOLD, threshold));
    }
}

