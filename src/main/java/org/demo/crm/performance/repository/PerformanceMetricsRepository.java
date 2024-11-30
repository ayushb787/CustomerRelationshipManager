package org.demo.crm.performance.repository;

import org.demo.crm.performance.model.PerformanceMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceMetricsRepository extends JpaRepository<PerformanceMetrics, Long> {

    // Find all performance metrics for a specific user
    List<PerformanceMetrics> findByUser_UserId(Long userId);

    // Find performance metrics by metric type (e.g., LEAD_CONVERSION, SALES_TARGET)
    List<PerformanceMetrics> findByMetricType(String metricType);

    // Find performance metrics within a date range
    List<PerformanceMetrics> findByDateBetween(java.util.Date startDate, java.util.Date endDate);

    // You can add more custom queries as needed
}
