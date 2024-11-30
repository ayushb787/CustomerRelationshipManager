package org.demo.crm.performance.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.demo.crm.auth.model.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "performance_metrics")
public class PerformanceMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetricType metricType;

    @Getter
    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = true)
    private String remarks;

    public enum MetricType {
        LEAD_CONVERSION,
        CUSTOMER_RETENTION,
        SALES_TARGET
    }

    // Getters and setters
    public Long getMetricId() {
        return metricId;
    }

    public void setMetricId(Long metricId) {
        this.metricId = metricId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public void setValue(Double value) {
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
