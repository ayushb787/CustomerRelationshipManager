package org.demo.crm.interaction.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "interactions")
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interactionId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long salespersonId;

    @Column(nullable = false)
    private String interactionType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition = "TEXT")
    private String details;

    private String attachments;
}
