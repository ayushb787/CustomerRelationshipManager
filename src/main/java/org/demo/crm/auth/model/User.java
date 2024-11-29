package org.demo.crm.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "Password is required")
    private String passwordHash;

    @Column(nullable = false)
    @NotNull(message = "Role is required")
    @Pattern(regexp = "^(Admin|Salesperson)$", message = "Role must be either 'Admin' or 'Salesperson'")
    private String role;

    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = true)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid")
    private String phoneNumber;

    @Column(nullable = true)
    private Boolean isActive = true;

    @Column(nullable = true)
    private java.time.LocalDateTime lastLogin;

}
