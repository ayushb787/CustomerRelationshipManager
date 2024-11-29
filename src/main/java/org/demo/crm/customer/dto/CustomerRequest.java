package org.demo.crm.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Phone number must be 10 digits"
    )
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Category is required")
    @Pattern(
            regexp = "^(Potential|Active|Inactive)$",
            message = "Category must be one of Potential, Active, or Inactive"
    )
    private String category;

    private String preferences;
}
