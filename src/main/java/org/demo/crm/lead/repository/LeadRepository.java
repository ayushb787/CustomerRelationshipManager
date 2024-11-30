package org.demo.crm.lead.repository;

import org.demo.crm.lead.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}
