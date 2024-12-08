package org.demo.crm.performance.service;

import org.demo.crm.performance.dto.PerformanceMetricsRequest;
import org.demo.crm.performance.dto.PerformanceMetricsResponse;
import org.demo.crm.performance.model.PerformanceMetrics;
import org.demo.crm.performance.repository.PerformanceMetricsRepository;
import org.demo.crm.auth.model.User;
import org.demo.crm.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformanceMetricsService {

    @Autowired
    private PerformanceMetricsRepository performanceMetricsRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PerformanceMetricsResponse> getAllPerformanceMetrics() {
        List<PerformanceMetrics> metrics = performanceMetricsRepository.findAll();
        return metrics.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PerformanceMetricsResponse createPerformanceMetrics(PerformanceMetricsRequest request) {
        PerformanceMetrics metrics = new PerformanceMetrics();

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        metrics.setUser(user);
        metrics.setMetricType(request.getMetricType());
        metrics.setValue(request.getValue());
        metrics.setDate(request.getDate());
        metrics.setRemarks(request.getRemarks());

        performanceMetricsRepository.save(metrics);

        return mapToResponse(metrics);
    }

    public PerformanceMetricsResponse updatePerformanceMetrics(Long id, PerformanceMetricsRequest request) {
        PerformanceMetrics metrics = performanceMetricsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Metric not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        metrics.setUser(user);
        metrics.setMetricType(request.getMetricType());
        metrics.setValue(request.getValue());
        metrics.setDate(request.getDate());
        metrics.setRemarks(request.getRemarks());

        performanceMetricsRepository.save(metrics);

        return mapToResponse(metrics);
    }

    private PerformanceMetricsResponse mapToResponse(PerformanceMetrics metrics) {
        PerformanceMetricsResponse response = new PerformanceMetricsResponse();
        response.setMetricId(metrics.getMetricId());
        response.setUserId(metrics.getUser().getUserId());
        response.setMetricType(metrics.getMetricType());
        response.setValue(metrics.getValue());
        response.setDate(metrics.getDate());
        response.setRemarks(metrics.getRemarks());
        return response;
    }
}
