package org.demo.crm.performance.dto;

import org.demo.crm.performance.model.PerformanceMetrics;

import java.time.LocalDateTime;

public class PerformanceMetricsRequest {

    private Long userId;
    private PerformanceMetrics.MetricType metricType;  // Use Enum directly
    private double value;
    private LocalDateTime date;  // Use LocalDateTime instead of Date
    private String remarks;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PerformanceMetrics.MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(PerformanceMetrics.MetricType metricType) {
        this.metricType = metricType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
