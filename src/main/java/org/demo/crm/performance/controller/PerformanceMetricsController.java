package org.demo.crm.performance.controller;

import org.demo.crm.auth.model.ApiResponse;
import org.demo.crm.performance.dto.PerformanceMetricsRequest;
import org.demo.crm.performance.dto.PerformanceMetricsResponse;
import org.demo.crm.performance.service.PerformanceMetricsService;
import org.demo.crm.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/performance-metrics")
public class PerformanceMetricsController {

    @Autowired
    private PerformanceMetricsService performanceMetricsService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ApiResponse<List<PerformanceMetricsResponse>> getAllPerformanceMetrics(HttpServletRequest httpRequest) {
        validateRequest(httpRequest);
        List<PerformanceMetricsResponse> metrics = performanceMetricsService.getAllPerformanceMetrics();
        return ApiResponse.success(metrics, "Performance metrics retrieved successfully");
    }

    @PostMapping
    public ApiResponse<PerformanceMetricsResponse> createPerformanceMetrics(
            @RequestBody PerformanceMetricsRequest request,
            HttpServletRequest httpRequest) {
        validateRequestForAdmin(httpRequest);  // Admin validation
        PerformanceMetricsResponse createdMetrics = performanceMetricsService.createPerformanceMetrics(request);
        return ApiResponse.success(createdMetrics, "Performance metric created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<PerformanceMetricsResponse> updatePerformanceMetrics(
            @PathVariable Long id,
            @RequestBody PerformanceMetricsRequest request,
            HttpServletRequest httpRequest) {
        validateRequestForAdmin(httpRequest);  // Admin validation
        PerformanceMetricsResponse updatedMetrics = performanceMetricsService.updatePerformanceMetrics(id, request);
        return ApiResponse.success(updatedMetrics, "Performance metric updated successfully");
    }

    // Validate JWT token
    private void validateRequest(HttpServletRequest httpRequest) {
        String token = jwtUtils.extractToken(httpRequest);
        if (token == null || !jwtUtils.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
    }

    private void validateRequestForAdmin(HttpServletRequest httpRequest) {
        String token = jwtUtils.extractToken(httpRequest);
        if (token == null || !jwtUtils.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        String role = jwtUtils.extractClaims(token).get("role", String.class);
        if (!"Admin".equals(role)) {
            throw new SecurityException("Access denied: Admins only");
        }
    }
}
