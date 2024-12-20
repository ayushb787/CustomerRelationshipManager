package org.demo.crm.customer.controller;

import jakarta.validation.Valid;
import org.demo.crm.auth.model.ApiResponse;
import org.demo.crm.customer.dto.CustomerRequest;
import org.demo.crm.customer.dto.CustomerResponse;
import org.demo.crm.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ApiResponse<Page<CustomerResponse>> getAllCustomers( @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> customers = customerService.getAllCustomers(pageable);
        return ApiResponse.success(customers, "Customers retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerResponse customer = customerService.getCustomerById(id);
        return ApiResponse.success(customer, "Customer retrieved successfully");
    }

    @PostMapping
    public ApiResponse<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse savedCustomer = customerService.createCustomer(customerRequest);
        return ApiResponse.success(savedCustomer, "Customer created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(id, customerRequest);
        return ApiResponse.success(updatedCustomer, "Customer updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ApiResponse.success("Customer deleted successfully", "Customer deleted successfully");
    }
}
