package org.demo.crm.sales.repository;

import org.demo.crm.sales.model.SalesPipeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesPipelineRepository extends JpaRepository<SalesPipeline, Long> {

    // Correct the query method to use the correct property name (leadId) in Lead entity
    List<SalesPipeline> findByLeadLeadId(Long leadId);  // Use leadLeadId to reference the lead's id
}
