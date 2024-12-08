package org.demo.crm.performance.repository;

import org.demo.crm.performance.model.PerformanceMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceMetricsRepository extends JpaRepository<PerformanceMetrics, Long> {

    List<PerformanceMetrics> findByUser_UserId(Long userId);

    List<PerformanceMetrics> findByMetricType(String metricType);

    List<PerformanceMetrics> findByDateBetween(java.util.Date startDate, java.util.Date endDate);

}
