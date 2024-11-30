package org.demo.crm.sales.repository;

import org.demo.crm.sales.model.SalesPipeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesPipelineRepository extends JpaRepository<SalesPipeline, Long> {


    List<SalesPipeline> findByLeadLeadId(Long leadId);
}
