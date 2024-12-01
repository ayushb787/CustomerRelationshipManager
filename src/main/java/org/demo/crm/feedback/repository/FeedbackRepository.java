package org.demo.crm.feedback.repository;

import org.demo.crm.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCustomerId(Long customerId);

    List<Feedback> findBySalespersonId(Long salespersonId);
}
