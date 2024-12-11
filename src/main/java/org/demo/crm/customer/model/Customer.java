package org.demo.crm.customer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;
    private String email;
    private String phone;
    private String address;
//    Potential, Active, or Inactive
    private String category;
//    Email, Phone, Whatsapp, or Other
    private String preferences;
    private LocalDateTime dateAdded;
}
