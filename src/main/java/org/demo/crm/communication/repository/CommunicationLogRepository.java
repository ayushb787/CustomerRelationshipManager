package org.demo.crm.communication.repository;

import org.demo.crm.communication.entity.CommunicationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationLogRepository extends JpaRepository<CommunicationLog, Long> {
}
