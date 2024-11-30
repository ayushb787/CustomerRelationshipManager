package org.demo.crm.communication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "communication_logs")
public class CommunicationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private Long customerId;
    private String channel; // e.g., "Email", "SMS"
    private String message;
    private Date date;
    private String status; // e.g., "Sent", "Failed"
}
