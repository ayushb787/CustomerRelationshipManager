package org.demo.crm.interaction.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InteractionResponse {

    private Long interactionId;
    private Long customerId;
    private Long salespersonId;
    private String interactionType;
    private Date date;
    private String details;
    private String attachments;
}
