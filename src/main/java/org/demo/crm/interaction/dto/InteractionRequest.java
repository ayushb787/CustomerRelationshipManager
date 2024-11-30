package org.demo.crm.interaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class InteractionRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private Long salespersonId;

    @NotBlank
    private String interactionType;

    @NotNull
    private Date date;

    private String details;

    private String attachments;
}
