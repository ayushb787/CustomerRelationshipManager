package org.demo.crm.customer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerResponse {
    private Long customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String category;
    private String preferences;
    private LocalDateTime dateAdded;
}
